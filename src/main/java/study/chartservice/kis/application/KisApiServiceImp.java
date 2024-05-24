package study.chartservice.kis.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import study.chartservice.kis.common.KisUrls;
import study.chartservice.kis.domain.KisAccessToken;

@Service
@RequiredArgsConstructor
public class KisApiServiceImp implements KisApiService {

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, String> redisTemplate;

	@Value("${kis.realApp.key}")
	private String appKey;

	@Value("${kis.realApp.secret}")
	private String appSecret;

	@Override
	public KisAccessToken getKisAccessToken() {
		return null;
	}

	private KisAccessToken createKisAccessToken() throws JsonProcessingException {
		// Request Body 설정
		Map<String, String> body = new HashMap<>();
		body.put("grant_type", "client_credentials");
		body.put("appkey", appKey);
		body.put("appsecret", appSecret);

		// Request Body JSON 변환
		String jsonBody = objectMapper.writeValueAsString(body);

		// RestTemplate 을 이용한 POST 통신
		ResponseEntity<String> response = restTemplate.exchange(
				KisUrls.TOKEN_PATH.getFullUrl(),
				HttpMethod.POST,
				new HttpEntity<>(jsonBody, null),
				String.class);

		// Response Body JSON 변환
		KisAccessToken kisAccessToken = objectMapper.readValue(response.getBody(),
				KisAccessToken.class);

		// 토큰 시간(86400초) 지정 후 Redis 에 토큰 저장
		redisTemplate.opsForValue()
				.set("kisAccessToken", objectMapper.writeValueAsString(kisAccessToken),
						Long.parseLong(kisAccessToken.getExpires_in()), TimeUnit.SECONDS);

		return kisAccessToken;
	}

}
