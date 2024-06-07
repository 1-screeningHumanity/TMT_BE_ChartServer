package study.chartservice.common;

import lombok.Getter;

@Getter
public enum StockIndex {
	KOSPI("0001"),
	KOSDAQ("1001");

	private final String value;

	StockIndex(String value) {
		this.value = value;
	}
}
