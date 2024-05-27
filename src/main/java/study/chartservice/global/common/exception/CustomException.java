package study.chartservice.global.common.exception;

import lombok.Getter;
import study.chartservice.global.common.response.BaseResponseCode;

@Getter
public class CustomException extends RuntimeException {

	private final BaseResponseCode status;

	public CustomException(BaseResponseCode status) {
		this.status = status;
	}
}
