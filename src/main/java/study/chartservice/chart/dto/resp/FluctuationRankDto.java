package study.chartservice.chart.dto.resp;

import lombok.Getter;

@Getter
public class FluctuationRankDto {
	private String id;
	private String dateTime;
	private String rankStatus;
	private String stockCode;
	private Integer data_rank;
	private String hts_kor_isnm;
	private String stck_prpr;
	private String prdy_vrss;
	private String prdy_ctrt;
	private String prdy_vrss_sign;
}
