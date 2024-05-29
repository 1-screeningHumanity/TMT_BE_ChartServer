package study.chartservice.chart.application;

import java.util.List;
import study.chartservice.chart.dto.resp.InvestorDto;
import study.chartservice.chart.dto.resp.StockDto;

public interface ChartService {
	List<StockDto> getChartOfDayByStockCode(String stockCode);
	List<StockDto> getChartOfWeekByStockCode(String stockCode);
	List<StockDto> getChartOfMonthByStockCode(String stockCode);
	List<StockDto> getChartOfYearByStockCode(String stockCode);
	List<InvestorDto> getInvestorByStockCode(String stockCode);
}
