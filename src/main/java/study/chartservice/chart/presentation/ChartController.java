package study.chartservice.chart.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.chartservice.chart.application.ChartService;
import study.chartservice.chart.application.CompanyInfoService;
import study.chartservice.chart.dto.resp.FluctuationRankDto;
import study.chartservice.chart.dto.resp.IndexOfStockDto;
import study.chartservice.chart.dto.resp.InvestorDto;
import study.chartservice.chart.dto.resp.StockAskingPriceDto;
import study.chartservice.chart.dto.resp.StockDto;
import study.chartservice.chart.dto.resp.StockMinDto;
import study.chartservice.chart.vo.resp.StockNameVo;
import study.chartservice.common.StockIndex;
import study.chartservice.common.StockRankOrder;
import study.chartservice.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
public class ChartController {

	private final CompanyInfoService companyInfoService;
	private final ModelMapper modelMapper;
	private final ChartService chartService;

	// 주식 이름 조회
	@GetMapping("/{stockCode}/name")
	public BaseResponse<StockNameVo> getCompanyName(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				modelMapper.map(companyInfoService.getCompanyNameByStockCode(stockCode),
						StockNameVo.class));
	}

	@GetMapping("/{stockCode}/asking-price")
	public BaseResponse<StockAskingPriceDto> getAskingPriceByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(chartService.getStockAskingPriceByStockCode(stockCode));
	}

	@GetMapping("/chart/{stockCode}/price")
	public BaseResponse<StockMinDto> getChartOfMinByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(chartService.getChartOfMinByStockCode(stockCode));
	}

	@GetMapping("/chart/{stockCode}/day")
	public BaseResponse<List<StockDto>> getChartOfDayByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(chartService.getChartOfDayByStockCode(stockCode));
	}

	@GetMapping("/chart/{stockCode}/week")
	public BaseResponse<List<StockDto>> getChartOfWeekByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(chartService.getChartOfWeekByStockCode(stockCode));
	}

	@GetMapping("/chart/{stockCode}/month")
	public BaseResponse<List<StockDto>> getChartOfMonthByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(chartService.getChartOfMonthByStockCode(stockCode));
	}

	@GetMapping("/chart/{stockCode}/year")
	public BaseResponse<List<StockDto>> getChartOfYearByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(chartService.getChartOfYearByStockCode(stockCode));
	}

	@GetMapping("/{stockCode}/investors")
	public BaseResponse<List<InvestorDto>> getInvestorByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(chartService.getInvestorByStockCode(stockCode));
	}

	@GetMapping("/mainpage/plummeting-stocks")
	public BaseResponse<List<FluctuationRankDto>> getFluctuationRankByDecrease() {
		return new BaseResponse<>(chartService.getFluctuationRankByDateTimeAndRankStatus(
				StockRankOrder.DECREASE.name()));
	}

	@GetMapping("/mainpage/soaring-stocks")
	public BaseResponse<List<FluctuationRankDto>> getFluctuationRankByIncrease() {
		return new BaseResponse<>(chartService.getFluctuationRankByDateTimeAndRankStatus(
				StockRankOrder.INCREASE.name()));
	}

	@GetMapping("/mainpage/kospi")
	public BaseResponse<IndexOfStockDto> getStockIndexKopsi() {
		return new BaseResponse<>(chartService.getIndexOfStockByIscd(StockIndex.KOSPI));
	}

	@GetMapping("/mainpage/kosdaq")
	public BaseResponse<IndexOfStockDto> getStockIndexKosdaq() {
		return new BaseResponse<>(chartService.getIndexOfStockByIscd(StockIndex.KOSDAQ));
	}
}
