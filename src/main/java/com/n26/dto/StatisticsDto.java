package com.n26.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsDto {
	private String sum;
	private String avg;
	private String max;
	private String min;
	private Long count;
}
