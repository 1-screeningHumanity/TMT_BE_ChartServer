package study.chartservice.chart.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.chartservice.chart.dto.resp.InvestorDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvestorVo {
	private String stockCode;
	private String stck_bsop_date;
	private String prsn_ntby_qty;
	private String frgn_ntby_qty;
	private String orgn_ntby_qty;
	private String prdy_vrss_sign;

	public static InvestorVo getInvestorDto(InvestorDto dto) {
		return InvestorVo.builder()
				.stockCode(dto.getStockCode())
				.stck_bsop_date(dto.getStck_bsop_date())
				.prsn_ntby_qty(dto.getPrsn_ntby_qty())
				.frgn_ntby_qty(dto.getFrgn_ntby_qty())
				.orgn_ntby_qty(dto.getOrgn_ntby_qty())
				.prdy_vrss_sign(dto.getPrdy_vrss_sign())
				.build();
	}

}


