package com.n26.exception;

public class TransactionExpiredException extends RuntimeException {
	public TransactionExpiredException() {
		super();
	}

	public TransactionExpiredException(String message) {
		super(message);
	}

	public TransactionExpiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
