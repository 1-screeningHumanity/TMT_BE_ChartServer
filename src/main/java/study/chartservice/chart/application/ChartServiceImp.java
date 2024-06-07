package study.chartservice.chart.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.chartservice.chart.domain.FluctuationRank;
import study.chartservice.chart.domain.Investor;
import study.chartservice.chart.domain.MinOfStock;
import study.chartservice.chart.dto.resp.FluctuationRankDto;
import study.chartservice.chart.dto.resp.InvestorDto;
import study.chartservice.chart.dto.resp.StockDto;
import study.chartservice.chart.dto.resp.StockMinDto;
import study.chartservice.chart.infrastructure.DayOfStockRepository;
import study.chartservice.chart.infrastructure.FluctuationRankRepository;
import study.chartservice.chart.infrastructure.InvestorRepository;
import study.chartservice.chart.infrastructure.MinOfStockRepository;
import study.chartservice.chart.infrastructure.MonthOfStockRepository;
import study.chartservice.chart.infrastructure.WeekOfStockRepository;
import study.chartservice.chart.infrastructure.YearOfStockRepository;
import study.chartservice.global.common.exception.CustomException;
import study.chartservice.global.common.response.BaseResponseCode;

@Service
@RequiredArgsConstructor
public class ChartServiceImp implements ChartService {

	private final ModelMapper modelMapper;
	private final MinOfStockRepository minOfStockRepository;
	private final DayOfStockRepository dayOfStockRepository;
	private final WeekOfStockRepository weekOfStockRepository;
	private final MonthOfStockRepository monthOfStockRepository;
	private final YearOfStockRepository yearOfStockRepository;
	private final InvestorRepository investorRepository;
	private final FluctuationRankRepository fluctuationRankRepository;

	@Override
	@Transactional(readOnly = true)
	public List<StockDto> getChartOfDayByStockCode(String stockCode) {
		return dayOfStockRepository.findByStockCode(stockCode).stream()
				.map(dayOfStock -> modelMapper.map(dayOfStock, StockDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockDto> getChartOfWeekByStockCode(String stockCode) {
		return weekOfStockRepository.findByStockCode(stockCode).stream()
				.map(weekOfStock -> modelMapper.map(weekOfStock, StockDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockDto> getChartOfMonthByStockCode(String stockCode) {
		return monthOfStockRepository.findByStockCode(stockCode).stream()
				.map(monthOfStock -> modelMapper.map(monthOfStock, StockDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockDto> getChartOfYearByStockCode(String stockCode) {
		return yearOfStockRepository.findByStockCode(stockCode).stream()
				.map(yearOfStock -> modelMapper.map(yearOfStock, StockDto.class))
				.toList();
	}

	@Override
	public StockMinDto getChartOfMinByStockCode(String stockCode) {
		MinOfStock minOfStock = minOfStockRepository.findByStockCode(stockCode)
				.orElseThrow(() -> new CustomException(BaseResponseCode.MIN_OF_STOCK_NOT_FOUND));

		return modelMapper.map(minOfStock, StockMinDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<InvestorDto> getInvestorByStockCode(String stockCode) {
		// 날짜 기준 상위 10개만 조회
		List<Investor> investors = investorRepository.findTopTenByStockCodeDesc(stockCode,
				PageRequest.of(0, 10));

		if (investors.isEmpty()) {
			throw new CustomException(BaseResponseCode.INVESTOR_NOT_FOUND);
		}

		return investors.stream()
				.map(investor -> modelMapper.map(investor, InvestorDto.class))
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<FluctuationRankDto> getFluctuationRankByDateTimeAndRankStatus(String rankStatus) {
		List<FluctuationRank> fluctuationRanks = fluctuationRankRepository.findByRankStatus(
				rankStatus);

		if (fluctuationRanks.isEmpty()) {
			throw new CustomException(BaseResponseCode.FLUCTUATION_RANK_NOT_FOUND);
		}

		return fluctuationRanks.stream()
				.map(fluctuationRank -> modelMapper.map(fluctuationRank, FluctuationRankDto.class))
				.toList();
	}
}
