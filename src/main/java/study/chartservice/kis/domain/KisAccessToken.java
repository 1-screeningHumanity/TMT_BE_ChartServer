package study.chartservice.kis.domain;

import lombok.Getter;

@Getter
public class KisAccessToken {
	private String access_token;
	private String token_type;
	private String expires_in;
	private String access_token_token_expired;
}
