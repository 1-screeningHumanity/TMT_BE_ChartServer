package study.chartservice.chart.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.chartservice.chart.dto.resp.StockNameDto;
import study.chartservice.chart.infrastructure.CompanyInfoRepository;
import study.chartservice.global.common.exception.CustomException;
import study.chartservice.global.common.response.BaseResponseCode;

@Service
@RequiredArgsConstructor
public class CompanyInfoServiceImp implements CompanyInfoService{
	private final CompanyInfoRepository companyInfoRepository;

	@Override
	public StockNameDto getCompanyNameByStockCode(String stockCode) {
		String stockName = companyInfoRepository.findByStockCode(stockCode)
				.orElseThrow(() -> new CustomException(BaseResponseCode.STOCK_NAME_NOT_FOUND))
				.getName();

		return StockNameDto.builder()
				.stockName(stockName)
				.build();
	}
}
