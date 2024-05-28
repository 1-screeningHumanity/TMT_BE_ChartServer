package study.chartservice.chart.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "investor")
public class Investor {
	@Id
	private String id;
	private String stockCode;
	private String stck_bsop_date;
	private String prsn_ntby_qty;
	private String frgn_ntby_qty;
	private String orgn_ntby_qty;
	private String prdy_vrss_sign;

	@Builder
	public Investor(String stockCode, String stck_bsop_date, String prsn_ntby_qty,
			String frgn_ntby_qty, String orgn_ntby_qty, String prdy_vrss_sign) {
		this.stockCode = stockCode;
		this.stck_bsop_date = stck_bsop_date;
		this.prsn_ntby_qty = prsn_ntby_qty;
		this.frgn_ntby_qty = frgn_ntby_qty;
		this.orgn_ntby_qty = orgn_ntby_qty;
		this.prdy_vrss_sign = prdy_vrss_sign;
	}
}
