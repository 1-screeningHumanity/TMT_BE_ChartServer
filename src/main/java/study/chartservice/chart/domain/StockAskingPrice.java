package study.chartservice.chart.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "stockaskingprice")
public class StockAskingPrice {
	@Id
	private String id;
	private String stockCode;
	private String aspr_acpt_hour;
	private String askp1;
	private String askp2;
	private String askp3;
	private String askp_rsqn1;
	private String askp_rsqn2;
	private String askp_rsqn3;
	private String bidp1;
	private String bidp2;
	private String bidp3;
	private String bidp_rsqn1;
	private String bidp_rsqn2;
	private String bidp_rsqn3;
	private String total_askp_rsqn;
	private String total_bidp_rsqn;

	@Builder
	public StockAskingPrice(String stockCode,String aspr_acpt_hour, String askp1, String askp2, String askp3,
			String askp_rsqn1, String askp_rsqn2, String askp_rsqn3, String bidp1, String bidp2,
			String bidp3, String bidp_rsqn1, String bidp_rsqn2, String bidp_rsqn3,
			String total_askp_rsqn, String total_bidp_rsqn) {
		this.stockCode = stockCode;
		this.aspr_acpt_hour = aspr_acpt_hour;
		this.askp1 = askp1;
		this.askp2 = askp2;
		this.askp3 = askp3;
		this.askp_rsqn1 = askp_rsqn1;
		this.askp_rsqn2 = askp_rsqn2;
		this.askp_rsqn3 = askp_rsqn3;
		this.bidp1 = bidp1;
		this.bidp2 = bidp2;
		this.bidp3 = bidp3;
		this.bidp_rsqn1 = bidp_rsqn1;
		this.bidp_rsqn2 = bidp_rsqn2;
		this.bidp_rsqn3 = bidp_rsqn3;
		this.total_askp_rsqn = total_askp_rsqn;
		this.total_bidp_rsqn = total_bidp_rsqn;
	}
}
