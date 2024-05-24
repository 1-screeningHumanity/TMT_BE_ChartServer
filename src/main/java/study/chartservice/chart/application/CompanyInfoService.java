package study.chartservice.chart.application;

import study.chartservice.chart.dto.resp.StockNameDto;

public interface CompanyInfoService {
	StockNameDto getCompanyNameByStockCode(String stockCode);
}
