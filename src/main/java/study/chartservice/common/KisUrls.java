package study.chartservice.common;

import lombok.Getter;

@Getter
public enum KisUrls {
	BASE_URL("https://openapi.koreainvestment.com:9443"),
	TOKEN_PATH("/oauth2/tokenP"),
	ITEM_CHART_PRICE_PATH("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice"),
	ITEM_CHART_PRICE_TIME_PATH("/uapi/domestic-stock/v1/quotations/inquire-time-itemchartprice"),
	STOCK_PRICE_INVESTOR_PATH("/uapi/domestic-stock/v1/quotations/inquire-investor"),
	STOCK_FLUCTUATION_RANK_PATH("/uapi/domestic-stock/v1/ranking/fluctuation"),
	STOCK_PRICE_INDEX_PATH("/uapi/domestic-stock/v1/quotations/inquire-index-price"),
	STOCK_ASKING_PRICE_PATH("/uapi/domestic-stock/v1/quotations/inquire-asking-price-exp-ccn");

	private final String path;

	KisUrls(String path) {
		this.path = path;
	}

	public String getFullUrl() {
		return BASE_URL.path + this.path;
	}
}
