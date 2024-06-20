package study.chartservice.chart.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.chartservice.chart.dto.resp.StockAskingPriceDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockAskingPriceVo {
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

	public static StockAskingPriceVo getStockAskingPriceDto(StockAskingPriceDto dto) {
		return StockAskingPriceVo.builder()
				.stockCode(dto.getStockCode())
				.aspr_acpt_hour(dto.getAspr_acpt_hour())
				.askp1(dto.getAskp1())
				.askp2(dto.getAskp2())
				.askp3(dto.getAskp3())
				.askp_rsqn1(dto.getAskp_rsqn1())
				.askp_rsqn2(dto.getAskp_rsqn2())
				.askp_rsqn3(dto.getAskp_rsqn3())
				.bidp1(dto.getBidp1())
				.bidp2(dto.getBidp2())
				.bidp3(dto.getBidp3())
				.bidp_rsqn1(dto.getBidp_rsqn1())
				.bidp_rsqn2(dto.getBidp_rsqn2())
				.bidp_rsqn3(dto.getBidp_rsqn3())
				.total_askp_rsqn(dto.getTotal_askp_rsqn())
				.total_bidp_rsqn(dto.getTotal_bidp_rsqn())
				.build();
	}
}
