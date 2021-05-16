package com.n26.service;

import com.n26.Application;
import com.n26.TestDataFactory;
import com.n26.dto.StatisticsDto;
import com.n26.dto.TransactionDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionServiceImplTest {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private TransactionStatisticsService transactionStatisticsService;

	@Test
	public void saveTransaction() {
		//Arrange
		TransactionDto transactionDto = TestDataFactory.getTransactionDto();
		//Act
		transactionService.saveTransaction(transactionDto);
		StatisticsDto stats = transactionStatisticsService.getTransactionStatistics();
		//Assert
		String transactionAmount = transactionDto.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		Assert.assertEquals(transactionAmount, stats.getAvg());
		Assert.assertEquals(transactionAmount, stats.getMax());
		Assert.assertEquals(transactionAmount, stats.getMin());
		Assert.assertEquals(transactionAmount, stats.getSum());
		Assert.assertEquals(Long.valueOf(1), stats.getCount());
	}

	@Test
	public void deleteAllTransactions() {
		//Arrange
		TransactionDto transactionDto = TestDataFactory.getTransactionDto();
		transactionService.saveTransaction(transactionDto);
		//Act
		transactionService.deleteAllTransactions();
		StatisticsDto stats = transactionStatisticsService.getTransactionStatistics();
		//Assert
		Assert.assertEquals("0.00", stats.getAvg());
		Assert.assertEquals("0.00", stats.getMax());
		Assert.assertEquals("0.00", stats.getMin());
		Assert.assertEquals("0.00", stats.getSum());
		Assert.assertEquals(Long.valueOf(0), stats.getCount());
	}
}
