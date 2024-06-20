package study.chartservice.chart.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.chartservice.chart.application.ChartService;
import study.chartservice.chart.application.CompanyInfoService;
import study.chartservice.chart.vo.resp.FluctuationRankVo;
import study.chartservice.chart.vo.resp.IndexOfStockVo;
import study.chartservice.chart.vo.resp.InvestorVo;
import study.chartservice.chart.vo.resp.StockAskingPriceVo;
import study.chartservice.chart.vo.resp.StockMinVo;
import study.chartservice.chart.vo.resp.StockNameVo;
import study.chartservice.chart.vo.resp.StockVo;
import study.chartservice.common.StockIndex;
import study.chartservice.common.StockRankOrder;
import study.chartservice.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
// 주의: Gateway 로드 밸런서 설정으로 인해 @RequestMapping("/stockitem")이 생략되었습니다.
// 로드 밸런서가 이 엔드포인트로의 라우팅을 처리합니다.
public class ChartController {

	private final CompanyInfoService companyInfoService;
	private final ChartService chartService;

	@GetMapping("/{stockCode}/name")
	public BaseResponse<StockNameVo> getCompanyName(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				StockNameVo.getStockNameDto(
						companyInfoService.getCompanyNameByStockCode(stockCode))
		);

	}

	@GetMapping("/{stockCode}/asking-price")
	public BaseResponse<StockAskingPriceVo> getAskingPriceByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				StockAskingPriceVo.getStockAskingPriceDto(
						chartService.getStockAskingPriceByStockCode(stockCode)));
	}

	@GetMapping("/chart/{stockCode}/price")
	public BaseResponse<StockMinVo> getChartOfMinByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				StockMinVo.getStockMinDto(chartService.getChartOfMinByStockCode(stockCode)));
	}

	@GetMapping("/chart/{stockCode}/day")
	public BaseResponse<List<StockVo>> getChartOfDayByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				chartService.getChartOfDayByStockCode(stockCode)
						.stream()
						.map(StockVo::getStockDto)
						.toList()
		);
	}

	@GetMapping("/chart/{stockCode}/week")
	public BaseResponse<List<StockVo>> getChartOfWeekByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				chartService.getChartOfWeekByStockCode(stockCode)
						.stream()
						.map(StockVo::getStockDto)
						.toList()
		);
	}

	@GetMapping("/chart/{stockCode}/month")
	public BaseResponse<List<StockVo>> getChartOfMonthByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				chartService.getChartOfMonthByStockCode(stockCode)
						.stream()
						.map(StockVo::getStockDto)
						.toList()
		);
	}

	@GetMapping("/chart/{stockCode}/year")
	public BaseResponse<List<StockVo>> getChartOfYearByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				chartService.getChartOfYearByStockCode(stockCode)
						.stream()
						.map(StockVo::getStockDto)
						.toList()
		);
	}

	@GetMapping("/{stockCode}/investors")
	public BaseResponse<List<InvestorVo>> getInvestorByStockCode(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				chartService.getInvestorByStockCode(stockCode)
						.stream()
						.map(InvestorVo::getInvestorDto)
						.toList()
		);
	}

	@GetMapping("/mainpage/plummeting-stocks")
	public BaseResponse<List<FluctuationRankVo>> getFluctuationRankByDecrease() {
		return new BaseResponse<>(
				chartService.getFluctuationRankByDateTimeAndRankStatus(
								StockRankOrder.DECREASE.name())
						.stream()
						.map(FluctuationRankVo::getFluctuationRankDto)
						.toList()
		);
	}

	@GetMapping("/mainpage/soaring-stocks")
	public BaseResponse<List<FluctuationRankVo>> getFluctuationRankByIncrease() {
		return new BaseResponse<>(
				chartService.getFluctuationRankByDateTimeAndRankStatus(
								StockRankOrder.INCREASE.name())
						.stream()
						.map(FluctuationRankVo::getFluctuationRankDto)
						.toList()
		);
	}

	@GetMapping("/mainpage/kospi")
	public BaseResponse<IndexOfStockVo> getStockIndexKopsi() {
		return new BaseResponse<>(IndexOfStockVo.getIndexOfStockDto(
				chartService.getIndexOfStockByIscd(StockIndex.KOSPI)));
	}

	@GetMapping("/mainpage/kosdaq")
	public BaseResponse<IndexOfStockVo> getStockIndexKosdaq() {
		return new BaseResponse<>(IndexOfStockVo.getIndexOfStockDto(
				chartService.getIndexOfStockByIscd(StockIndex.KOSDAQ)));
	}
}
