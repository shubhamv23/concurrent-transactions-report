package com.n26;

import com.n26.datastore.entity.TransactionStatisticsFrame;
import com.n26.dto.TransactionDto;
import lombok.experimental.UtilityClass;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static java.time.ZoneOffset.UTC;

@UtilityClass
public class TestDataFactory {

	private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX").withZone(UTC);

	public TransactionDto getTransactionDto() {
		return TransactionDto.builder()
				.amount(BigDecimal.valueOf(new Random().nextDouble()))
				.timestamp(LocalDateTime.now(UTC))
				.build();
	}

	public String getAddTransactionRequest(LocalDateTime localDateTime) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("amount", BigDecimal.valueOf(new Random().nextDouble()).toString());
		jsonObject.put("timestamp", localDateTime.atZone(UTC).format(TIMESTAMP_FORMATTER));
		return jsonObject.toString();
	}

	public static TransactionStatisticsFrame getTransactionStatFrame() {
		return TransactionStatisticsFrame.builder()
				.count(10L)
				.max(BigDecimal.valueOf(4314.33))
				.min(BigDecimal.valueOf(32.32))
				.sum(BigDecimal.valueOf(6898.34))
				.average(BigDecimal.valueOf(3456.11))
				.build();
	}

}
