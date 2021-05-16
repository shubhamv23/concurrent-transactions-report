package com.n26.datastore;

import com.n26.datastore.entity.Transaction;
import com.n26.datastore.entity.TransactionStatisticsFrame;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class TransactionRepositoryImpl implements TransactionRepository {

	private static final int CAPACITY = 60;
	private Map<Integer, TransactionStatisticsFrame> transactionStats;

	public TransactionRepositoryImpl() {
		this.transactionStats = new ConcurrentHashMap<>(CAPACITY);
	}

	@Override
	public TransactionStatisticsFrame getTransactionStatistics() {
		log.info("Computing statistics from last {} seconds transactionStats", CAPACITY);
		TransactionStatisticsFrame cumulativeTransactionStats = this.transactionStats.values().stream()
				.filter(Objects::nonNull)
				.filter(TransactionStatisticsFrame::isStillValid)
				.reduce((stat1, stat2) ->
						TransactionStatisticsFrame.builder()
								.min(stat1.min(stat2))
								.max(stat1.max(stat2))
								.count(stat1.getCount() + stat2.getCount())
								.sum(stat1.getSum().add(stat2.getSum()))
								.build())
				.orElseGet(TransactionStatisticsFrame::new);
		cumulativeTransactionStats.setAverage(cumulativeTransactionStats.average());
		log.debug("Cumulative transaction stats: {}", cumulativeTransactionStats.toString());
		return cumulativeTransactionStats;
	}

	@Override
	public void saveTransaction(Transaction transaction) {
		log.info("Adding transaction to transactionStats in TransactionDataStore");
		int index = getHashedIndex(transaction);
		this.transactionStats.compute(index, (key, prevFrame) -> {
			if (prevFrame != null && prevFrame.isStillValid()) {
				log.debug("TransactionStatisticsFrame at index: {} is still valid, combining new transaction data", index);
				log.debug("Value of TransactionStatisticsFrame: {}", prevFrame.toString());
				return updateFrame(prevFrame, transaction);
			}
			return updateFrame(new TransactionStatisticsFrame(), transaction);
		});
	}

	@Override
	public void deleteAllTransaction() {
		log.info("Deleting all transactions statistics from TransactionDataStore");
		this.transactionStats.clear();
	}

	private TransactionStatisticsFrame updateFrame(TransactionStatisticsFrame prevFrame, Transaction transaction) {
		log.debug("Updating previous transactionStat frame: {} w.r.t new upcoming transaction: {}", prevFrame.toString(), transaction.toString());
		return TransactionStatisticsFrame.builder()
				.average(transaction.getAmount())
				.max(transaction.getAmount().max(prevFrame.getMax()))
				.min(prevFrame.getCount() == 0 ? transaction.getAmount() : transaction.getAmount().min(prevFrame.getMin()))
				.sum(transaction.getAmount().add(prevFrame.getSum()))
				.count(prevFrame.getCount() + 1)
				.lastUpdated(transaction.getTimestamp())
				.build();
	}

	private int getHashedIndex(Transaction transaction) {
		return (int) transaction.getTimestamp().toEpochSecond(ZoneOffset.UTC) % CAPACITY;
	}
}
