package com.n26.datastore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Slf4j
@AllArgsConstructor
@Builder
@Data
@ToString(of = { "lastUpdated", "sum", "count", "min", "max", "average" })
public final class TransactionStatisticsFrame {
	private BigDecimal sum;
	private BigDecimal average;
	private BigDecimal min;
	private BigDecimal max;
	private Long count;
	private LocalDateTime lastUpdated;

	public TransactionStatisticsFrame() {
		this.sum = BigDecimal.ZERO;
		this.average = BigDecimal.ZERO;
		this.min = BigDecimal.ZERO;
		this.max = BigDecimal.ZERO;
		this.count = 0L;
	}

	public boolean isStillValid() {
		log.debug("Checking if transaction frame is still valid or not: {}", this.toString());
		if (this.lastUpdated == null) {
			return false;
		}
		return this.lastUpdated.isAfter(LocalDateTime.now(ZoneOffset.UTC).minusSeconds(60));
	}

	public BigDecimal min(TransactionStatisticsFrame stat2) {
		if (this.count == null || this.count == 0) {
			return stat2.min;
		}
		return this.min.min(stat2.getMin());
	}

	public BigDecimal max(TransactionStatisticsFrame stat2) {
		return this.max.max(stat2.getMax());
	}

	public BigDecimal average() {
		if (this.count == null || this.count == 0L) {
			return this.sum;
		}
		return this.sum.divide(BigDecimal.valueOf(this.count), 2, ROUND_HALF_UP);
	}

}

