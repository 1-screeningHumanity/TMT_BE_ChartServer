package study.chartservice.chart.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.chartservice.chart.dto.resp.FluctuationRankDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FluctuationRankVo {
	private String id;
	private String dateTime;
	private String rankStatus;
	private String stockCode;
	private Integer data_rank;
	private String hts_kor_isnm;
	private String stck_prpr;
	private String prdy_vrss;
	private String prdy_ctrt;
	private String prdy_vrss_sign;

	public static FluctuationRankVo getFluctuationRankDto(FluctuationRankDto dto) {
		return FluctuationRankVo.builder()
				.id(dto.getId())
				.dateTime(dto.getDateTime())
				.rankStatus(dto.getRankStatus())
				.stockCode(dto.getStockCode())
				.data_rank(dto.getData_rank())
				.hts_kor_isnm(dto.getHts_kor_isnm())
				.stck_prpr(dto.getStck_prpr())
				.prdy_vrss(dto.getPrdy_vrss())
				.prdy_ctrt(dto.getPrdy_ctrt())
				.prdy_vrss_sign(dto.getPrdy_vrss_sign())
				.build();
	}
}
