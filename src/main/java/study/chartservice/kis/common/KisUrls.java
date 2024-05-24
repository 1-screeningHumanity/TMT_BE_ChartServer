package study.chartservice.kis.common;

import lombok.Getter;

@Getter
public enum KisUrls {
	BASE_URL("https://openapi.koreainvestment.com:9443"),
	TOKEN_PATH("/oauth2/tokenP");

	private final String path;

	KisUrls(String path) {
		this.path = path;
	}

	public String getFullUrl() {
		return BASE_URL.path + this.path;
	}
}
