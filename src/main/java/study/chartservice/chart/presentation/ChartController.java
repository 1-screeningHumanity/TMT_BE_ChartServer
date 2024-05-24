package study.chartservice.chart.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.chartservice.chart.application.CompanyInfoService;
import study.chartservice.chart.vo.resp.StockNameVo;
import study.chartservice.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
public class ChartController {

	private final CompanyInfoService companyInfoService;
	private final ModelMapper modelMapper;

	// 주식 이름 조회
	@GetMapping("/stockitem/{stockCode}/name")
	public BaseResponse<StockNameVo> getCompanyName(
			@PathVariable("stockCode") String stockCode
	) {
		return new BaseResponse<>(
				modelMapper.map(companyInfoService.getCompanyNameByStockCode(stockCode),
						StockNameVo.class));
	}

}
