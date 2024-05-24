package study.chartservice.chart.dto.resp;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StockNameDto {
	private String stockName;

	@Builder
	public StockNameDto(String stockName) {
		this.stockName = stockName;
	}
}
