package com.n26.controller;

import com.n26.dto.StatisticsDto;
import com.n26.service.TransactionStatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/statistics")
public class TransactionStatisticsController {

	private final TransactionStatisticsService transactionStatisticsService;

	@GetMapping
	public ResponseEntity<StatisticsDto> getStatistics() {
		log.info("Fetching transaction stats at : {}", LocalDateTime.now(ZoneOffset.UTC));
		return ResponseEntity.ok(transactionStatisticsService.getTransactionStatistics());
	}

}
