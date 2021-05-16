package com.n26.service;

import com.n26.dto.TransactionDto;

public interface TransactionService {

	void saveTransaction(TransactionDto transaction);

	void deleteAllTransactions();
}
