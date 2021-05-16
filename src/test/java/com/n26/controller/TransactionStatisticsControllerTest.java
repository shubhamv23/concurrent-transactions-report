package com.n26.controller;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionStatisticsControllerTest extends RestControllerTest {

	private static final String TRANSACTION_STATS_ENDPOINT = "/statistics";

	@Test
	public void addTransactionWithTimeStampInRange() throws Exception {
		mockMvc.perform(get(TRANSACTION_STATS_ENDPOINT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sum", is("0.00")))
				.andExpect(jsonPath("$.avg", is("0.00")))
				.andExpect(jsonPath("$.min", is("0.00")))
				.andExpect(jsonPath("$.max", is("0.00")))
				.andExpect(jsonPath("$.count", is(0)));
	}

}
