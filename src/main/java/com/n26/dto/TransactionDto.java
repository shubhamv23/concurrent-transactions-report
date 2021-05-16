package com.n26.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString(of = { "amount", "timestamp" })
public class TransactionDto {
	@NotNull(message = "Transaction amount is required")
	private BigDecimal amount;
	@NotNull(message = "Transaction timestamp is required")
	private LocalDateTime timestamp;
}
