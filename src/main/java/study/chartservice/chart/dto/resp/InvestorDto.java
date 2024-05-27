package study.chartservice.chart.dto.resp;

import lombok.Getter;

@Getter
public class InvestorDto {
	private String stockCode;
	private String stck_bsop_date;
	private String prsn_ntby_qty;
	private String frgn_ntby_qty;
	private String orgn_ntby_qty;
	private String prdy_vrss_sign;
}
