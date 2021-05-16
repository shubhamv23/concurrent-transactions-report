package com.n26.service;

import com.n26.datastore.TransactionRepository;
import com.n26.datastore.entity.Transaction;
import com.n26.dto.TransactionDto;
import com.n26.exception.FutureTransactionException;
import com.n26.exception.TransactionExpiredException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

@Slf4j
@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;

	@Override
	public void saveTransaction(TransactionDto transactionDto) {
		log.debug("Processing save transaction request for : {}", transactionDto.toString());
		validateTransaction(transactionDto);
		transactionRepository.saveTransaction(Transaction.builder()
				.amount(transactionDto.getAmount())
				.timestamp(transactionDto.getTimestamp())
				.build());
	}

	@Override
	public void deleteAllTransactions() {
		log.debug("Deleting all transactions");
		transactionRepository.deleteAllTransaction();
	}

	private void validateTransaction(TransactionDto transactionDto) {
		log.debug("Validating transaction");
		LocalDateTime currentTime = LocalDateTime.now(UTC);
		if (transactionDto.getTimestamp().isBefore(currentTime.minusSeconds(60))) {
			throw new TransactionExpiredException();
		}
		if (transactionDto.getTimestamp().isAfter(currentTime)) {
			throw new FutureTransactionException();
		}
	}

}
