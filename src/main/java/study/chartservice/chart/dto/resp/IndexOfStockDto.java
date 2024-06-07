package study.chartservice.chart.dto.resp;

import lombok.Getter;

@Getter
public class IndexOfStockDto {

	private String iscd;   // 종목 코스피, 코스닥
	private String dateTime;    // 날짜 시간
	private String bstp_nmix_prpr; //	업종 지수
	private String bstp_nmix_prdy_vrss; //	업종 지수 전일 대비
	private String prdy_vrss_sign; //	전일 대비 부호
	private String bstp_nmix_prdy_ctrt; //	업종 지수 전일 대비율
}
