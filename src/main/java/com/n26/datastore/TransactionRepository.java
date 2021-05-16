package com.n26.datastore;

import com.n26.datastore.entity.Transaction;
import com.n26.datastore.entity.TransactionStatisticsFrame;

public interface TransactionRepository {

	TransactionStatisticsFrame getTransactionStatistics();

	void saveTransaction(Transaction transaction);

	void deleteAllTransaction();
}
