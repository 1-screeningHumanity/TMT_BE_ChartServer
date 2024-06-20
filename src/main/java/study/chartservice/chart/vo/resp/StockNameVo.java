package study.chartservice.chart.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.chartservice.chart.dto.resp.StockNameDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockNameVo {
	private String stockName;

	public static StockNameVo getStockNameDto(StockNameDto dto) {
		return StockNameVo.builder()
				.stockName(dto.getStockName())
				.build();
	}
}
