package study.chartservice.chart.application;

import java.util.List;
import study.chartservice.chart.dto.resp.FluctuationRankDto;
import study.chartservice.chart.dto.resp.IndexOfStockDto;
import study.chartservice.chart.dto.resp.InvestorDto;
import study.chartservice.chart.dto.resp.StockDto;
import study.chartservice.chart.dto.resp.StockMinDto;
import study.chartservice.common.StockIndex;

public interface ChartService {
	List<StockDto> getChartOfDayByStockCode(String stockCode);
	List<StockDto> getChartOfWeekByStockCode(String stockCode);
	List<StockDto> getChartOfMonthByStockCode(String stockCode);
	List<StockDto> getChartOfYearByStockCode(String stockCode);
	StockMinDto getChartOfMinByStockCode(String stockCode);
	List<InvestorDto> getInvestorByStockCode(String stockCode);
	List<FluctuationRankDto> getFluctuationRankByDateTimeAndRankStatus(String rankStatus);
	IndexOfStockDto getIndexOfStockByIscd(StockIndex iscd);

}
