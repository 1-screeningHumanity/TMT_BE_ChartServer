package study.chartservice.chart.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "monthofstock")
public class MonthOfStock {
	@Id
	private String id;
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

	@Builder
	public MonthOfStock(String stockCode, String stck_bsop_date, String stck_clpr, String stck_oprc,
			String stck_hgpr, String stck_lwpr, String acml_vol, String acml_tr_pbmn,
			String prdy_vrss,
			String prdy_vrss_sign) {
		this.stockCode = stockCode;
		this.stck_bsop_date = stck_bsop_date;
		this.stck_clpr = stck_clpr;
		this.stck_oprc = stck_oprc;
		this.stck_hgpr = stck_hgpr;
		this.stck_lwpr = stck_lwpr;
		this.acml_vol = acml_vol;
		this.acml_tr_pbmn = acml_tr_pbmn;
		this.prdy_vrss = prdy_vrss;
		this.prdy_vrss_sign = prdy_vrss_sign;
	}
}
