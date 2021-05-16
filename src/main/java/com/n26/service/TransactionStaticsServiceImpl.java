package com.n26.service;

import com.n26.datastore.TransactionRepository;
import com.n26.datastore.entity.TransactionStatisticsFrame;
import com.n26.dto.StatisticsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
@AllArgsConstructor
public class TransactionStaticsServiceImpl implements TransactionStatisticsService {

	private final TransactionRepository transactionRepository;

	@Override
	public StatisticsDto getTransactionStatistics() {
		TransactionStatisticsFrame statistics = transactionRepository.getTransactionStatistics();
		return StatisticsDto.builder()
				.sum(statistics.getSum().setScale(2, ROUND_HALF_UP).toString())
				.min(statistics.getMin().setScale(2, ROUND_HALF_UP).toString())
				.max(statistics.getMax().setScale(2, ROUND_HALF_UP).toString())
				.avg(statistics.getAverage().setScale(2, ROUND_HALF_UP).toString())
				.count(statistics.getCount())
				.build();
	}
}
