package study.chartservice.common;

import lombok.Getter;

@Getter
public enum StockRankOrder {
	INCREASE("0"),
	DECREASE("1");

	private final String value;

	StockRankOrder(String value) {
		this.value = value;
	}
}
