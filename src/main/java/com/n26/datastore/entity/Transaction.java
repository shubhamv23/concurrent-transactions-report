package com.n26.datastore.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString(of = { "amount", "timestamp" })
public class Transaction {
	private BigDecimal amount;
	private LocalDateTime timestamp;
}
