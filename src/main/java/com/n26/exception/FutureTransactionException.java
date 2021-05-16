package com.n26.exception;

public class FutureTransactionException extends RuntimeException {
	public FutureTransactionException() {
		super();
	}

	public FutureTransactionException(String message) {
		super();
	}

	public FutureTransactionException(String message, Throwable cause) {
		super(message, cause);
	}
}
