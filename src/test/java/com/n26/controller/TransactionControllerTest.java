package com.n26.controller;

import com.n26.TestDataFactory;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest extends RestControllerTest {

	private static final String TRANSACTION_ENDPOINT = "/transactions";

	@Test
	public void addTransactionWithTimeStampInRange() throws Exception {
		mockMvc.perform(post(TRANSACTION_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestDataFactory.getAddTransactionRequest(LocalDateTime.now(UTC))))
				.andExpect(status().isCreated());
	}

	@Test
	public void addTransactionWithExpiredTimeStamp() throws Exception {
		mockMvc.perform(post(TRANSACTION_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestDataFactory.getAddTransactionRequest(LocalDateTime.now(UTC).minusMinutes(5))))
				.andExpect(status().isNoContent());
	}

	@Test
	public void addTransactionWithFutureTimeStamp() throws Exception {
		mockMvc.perform(post(TRANSACTION_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestDataFactory.getAddTransactionRequest(LocalDateTime.now(UTC).plusMinutes(5))))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void addTransactionWithInvalidDateFormat() throws Exception {
		mockMvc.perform(post(TRANSACTION_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
						+ "\"amount\": \"12.3343\",\n"
						+ "\"timestamp\": \"2018-07-17T9:59:51.312Z\"\n"
						+ "}"))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void deleteAllTransaction() throws Exception {
		mockMvc.perform(delete(TRANSACTION_ENDPOINT))
				.andExpect(status().isNoContent());
	}

}
