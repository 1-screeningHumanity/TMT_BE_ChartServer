package study.chartservice.common;

import lombok.Getter;

@Getter
public enum StockRankOrder {
	INCREASE("2"),
	DECREASE("3");

	private final String value;

	StockRankOrder(String value) {
		this.value = value;
	}
}
