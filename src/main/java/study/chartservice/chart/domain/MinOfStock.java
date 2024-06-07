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
	private String stck_bsop_date; // 주식 영업 일자
	private String stck_cntg_hour; // 주식 체결 시간
	private String acml_tr_pbmn; // 누적 거래 대금
	private String stck_prpr; // 주식 현재가
	private String stck_oprc;   // 주식 시가2
	private String stck_hgpr; // 주식 최고가
	private String stck_lwpr; // 주식 최저가
	private String cntg_vol; // 체결 거래량

	@Builder
	public MinOfStock(String stockCode, String stck_bsop_date, String stck_cntg_hour,
			String acml_tr_pbmn, String stck_prpr, String stck_oprc, String stck_hgpr,
			String stck_lwpr,
			String cntg_vol) {
		this.stockCode = stockCode;
		this.stck_bsop_date = stck_bsop_date;
		this.stck_cntg_hour = stck_cntg_hour;
		this.acml_tr_pbmn = acml_tr_pbmn;
		this.stck_prpr = stck_prpr;
		this.stck_oprc = stck_oprc;
		this.stck_hgpr = stck_hgpr;
		this.stck_lwpr = stck_lwpr;
		this.cntg_vol = cntg_vol;
	}
}
