package study.chartservice.kis.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import study.chartservice.kis.domain.KisAccessToken;

// 한국투자증권 API 서비스
public interface KisApiService {

	KisAccessToken getKisAccessToken() throws JsonProcessingException;
}
