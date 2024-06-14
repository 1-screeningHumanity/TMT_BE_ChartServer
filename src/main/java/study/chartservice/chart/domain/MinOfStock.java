package study.chartservice.chart.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "minofstock")
public class MinOfStock {
	@Id
	private String id;
	private String stockCode; // 주식 코드
	private String stockCreatAt; // 주식 영업 일자
	private String prdy_vrss;   // 	전일 대비
	private String prdy_vrss_sign;  //  전일 대비 부호
	private String prdy_ctrt;   // 전일 대비율
	private String stck_prdy_clpr;  //  주식 전일 종가
	private String acml_vol;    // 누적 거래량
	private String acml_tr_pbmn;    //  누적 거래 대금
	private String hts_kor_isnm;    // HTS 한글 종목명
	private String stck_prpr;   // 주식 현재가


	@Builder
	public MinOfStock(String stockCode, String stockCreatAt, String prdy_vrss,
			String prdy_vrss_sign, String prdy_ctrt, String stck_prdy_clpr, String acml_vol,
			String acml_tr_pbmn, String hts_kor_isnm, String stck_prpr) {
		this.stockCode = stockCode;
		this.stockCreatAt = stockCreatAt;
		this.prdy_vrss = prdy_vrss;
		this.prdy_vrss_sign = prdy_vrss_sign;
		this.prdy_ctrt = prdy_ctrt;
		this.stck_prdy_clpr = stck_prdy_clpr;
		this.acml_vol = acml_vol;
		this.acml_tr_pbmn = acml_tr_pbmn;
		this.hts_kor_isnm = hts_kor_isnm;
		this.stck_prpr = stck_prpr;
	}
}
