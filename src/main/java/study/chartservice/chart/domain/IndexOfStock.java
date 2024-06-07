package study.chartservice.chart.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "indexofstock")
public class IndexOfStock {
	@Id
	private String id;
	private String iscd;   // 종목 코스피, 코스닥
	private String dateTime;    // 날짜 시간
	private String bstp_nmix_prpr; //	업종 지수
	private String bstp_nmix_prdy_vrss; //	업종 지수 전일 대비
	private String prdy_vrss_sign; //	전일 대비 부호
	private String bstp_nmix_prdy_ctrt; //	업종 지수 전일 대비율

	@Builder
	public IndexOfStock(String iscd, String dateTime, String bstp_nmix_prpr,
			String bstp_nmix_prdy_vrss,
			String prdy_vrss_sign, String bstp_nmix_prdy_ctrt) {

		this.iscd = iscd;
		this.dateTime = dateTime;
		this.bstp_nmix_prpr = bstp_nmix_prpr;
		this.bstp_nmix_prdy_vrss = bstp_nmix_prdy_vrss;
		this.prdy_vrss_sign = prdy_vrss_sign;
		this.bstp_nmix_prdy_ctrt = bstp_nmix_prdy_ctrt;
	}
}
