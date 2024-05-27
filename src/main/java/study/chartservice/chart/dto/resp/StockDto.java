package study.chartservice.chart.dto.resp;

import lombok.Getter;

@Getter
public class StockDto {
	private String id;  //	주식 id
	private String stockCode;   //	주식 코드
	private String stck_bsop_date;  //	주식 영업 일자
	private String stck_clpr;   //	주식 종가
	private String stck_oprc;   //	주식 시가
	private String stck_hgpr;   //	주식 최고가
	private String stck_lwpr;   //	주식 최저가
	private String acml_vol;    //	누적 거래량
	private String acml_tr_pbmn;    //	누적 거래 대금
	private String prdy_vrss;   //	전일 대비
	private String prdy_vrss_sign;  //	전일 대비 부호
}
