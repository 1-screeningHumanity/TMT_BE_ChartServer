package study.chartservice.kis.dto.resp;

import lombok.Getter;

@Getter
public class StockTimeDataDto {
	private String prdy_vrss;   // 	전일 대비
	private String prdy_vrss_sign;  //  전일 대비 부호
	private String prdy_ctrt;   // 전일 대비율
	private String stck_prdy_clpr;  //  주식 전일 종가
	private String acml_vol;    // 누적 거래량
	private String acml_tr_pbmn;    //  누적 거래 대금
	private String hts_kor_isnm;    // HTS 한글 종목명
	private String stck_prpr;   // 주식 현재가
}