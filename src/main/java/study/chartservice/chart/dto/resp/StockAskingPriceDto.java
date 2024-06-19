package study.chartservice.chart.dto.resp;

import lombok.Getter;

@Getter
public class StockAskingPriceDto {
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
}
