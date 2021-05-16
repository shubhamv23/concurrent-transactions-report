package com.n26.service;

import com.n26.Application;
import com.n26.TestDataFactory;
import com.n26.datastore.TransactionRepositoryImpl;
import com.n26.datastore.entity.TransactionStatisticsFrame;
import com.n26.dto.StatisticsDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionStaticsServiceImplTest {

	@InjectMocks
	private TransactionStaticsServiceImpl transactionStatisticsService;

	@Mock
	private TransactionRepositoryImpl transactionRepository;

	@Test
	public void getTransactionStatistics() {
		//Arrange
		TransactionStatisticsFrame statisticsFrame = TestDataFactory.getTransactionStatFrame();
		when(transactionRepository.getTransactionStatistics()).thenReturn(statisticsFrame);
		//Act
		StatisticsDto stats = transactionStatisticsService.getTransactionStatistics();
		//Assert
		Assert.assertEquals(statisticsFrame.getAverage().toString(), stats.getAvg());
		Assert.assertEquals(statisticsFrame.getMax().toString(), stats.getMax());
		Assert.assertEquals(statisticsFrame.getMin().toString(), stats.getMin());
		Assert.assertEquals(statisticsFrame.getSum().toString(), stats.getSum());
		Assert.assertEquals(statisticsFrame.getCount(), stats.getCount());
		verify(transactionRepository, times(1)).getTransactionStatistics();
	}

}
