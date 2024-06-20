package study.chartservice.chart.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.chartservice.chart.dto.resp.IndexOfStockDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndexOfStockVo {
	private String iscd;   // 종목 코스피, 코스닥
	private String dateTime;    // 날짜 시간
	private String bstp_nmix_prpr; //	업종 지수
	private String bstp_nmix_prdy_vrss; //	업종 지수 전일 대비
	private String prdy_vrss_sign; //	전일 대비 부호
	private String bstp_nmix_prdy_ctrt; //	업종 지수 전일 대비율

	public static IndexOfStockVo getIndexOfStockDto(IndexOfStockDto dto) {
		return IndexOfStockVo.builder()
				.iscd(dto.getIscd())
				.dateTime(dto.getDateTime())
				.bstp_nmix_prpr(dto.getBstp_nmix_prpr())
				.bstp_nmix_prdy_vrss(dto.getBstp_nmix_prdy_vrss())
				.prdy_vrss_sign(dto.getPrdy_vrss_sign())
				.bstp_nmix_prdy_ctrt(dto.getBstp_nmix_prdy_ctrt())
				.build();
	}

}
