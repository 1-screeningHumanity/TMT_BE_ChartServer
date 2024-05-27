package study.chartservice.chart.application;

import java.util.List;
import study.chartservice.chart.dto.resp.InvestorDto;
import study.chartservice.kis.dto.resp.InvestorDataDto;
import study.chartservice.chart.dto.resp.StockDto;

public interface ChartService {
	List<StockDto> getChartOfDayByStockCode(String stockCode);
	List<StockDto> getChartOfWeekByStockCode(String stockCode);
	List<StockDto> getChartOfMonthByStockCode(String stockCode);
	List<StockDto> getChartOfYearByStockCode(String stockCode);
	InvestorDto getInvestorByStockCode(String stockCode);
}
