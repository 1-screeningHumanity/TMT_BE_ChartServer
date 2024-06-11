package study.chartservice.chart.dto.resp;

import lombok.Getter;

@Getter
public class StockMinDto {
	private String stockCode;   // 주식 코드
	private String stck_cntg_hour; //	주식 체결 시간
	private String stck_prpr; //	주식 현재가
	private String stck_hgpr; //	주식 최고가
	private String stck_lwpr; //	주식 최저가
	private String cntg_vol; //	체결 거래량
}
