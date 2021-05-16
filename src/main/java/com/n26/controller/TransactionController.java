package com.n26.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.dto.TransactionDto;
import com.n26.exception.FutureTransactionException;
import com.n26.exception.TransactionExpiredException;
import com.n26.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping
	public ResponseEntity addTransaction(@RequestBody @Valid TransactionDto transactionDto) {
		log.info("New request to add transaction: {}", transactionDto.toString());
		try {
			transactionService.saveTransaction(transactionDto);
		} catch (FutureTransactionException ex) {
			log.error("Transaction timestamp is of future");
			return ResponseEntity.unprocessableEntity().build();
		} catch (TransactionExpiredException ex) {
			log.error("Transaction timestamp is older than defined limit");
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping
	public ResponseEntity<String> deleteAllTransaction() {
		log.info("New request to delete all transactions!");
		transactionService.deleteAllTransactions();
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity handleJacksonMappingError(InvalidFormatException ex) {
		return ResponseEntity.unprocessableEntity().build();
	}
}
