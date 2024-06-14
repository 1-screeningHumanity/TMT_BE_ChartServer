package study.chartservice.kis.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import study.chartservice.chart.domain.CompanyInfo;
import study.chartservice.chart.domain.DayOfStock;
import study.chartservice.chart.domain.FluctuationRank;
import study.chartservice.chart.domain.IndexOfStock;
import study.chartservice.chart.domain.Investor;
import study.chartservice.chart.domain.MinOfStock;
import study.chartservice.chart.domain.MonthOfStock;
import study.chartservice.chart.domain.StockAskingPrice;
import study.chartservice.chart.domain.WeekOfStock;
import study.chartservice.chart.domain.YearOfStock;
import study.chartservice.chart.infrastructure.CompanyInfoRepository;
import study.chartservice.chart.infrastructure.DayOfStockRepository;
import study.chartservice.chart.infrastructure.FluctuationRankRepository;
import study.chartservice.chart.infrastructure.IndexOfStockRepository;
import study.chartservice.chart.infrastructure.InvestorRepository;
import study.chartservice.chart.infrastructure.MinOfStockRepository;
import study.chartservice.chart.infrastructure.MonthOfStockRepository;
import study.chartservice.chart.infrastructure.StockAskingPriceRepository;
import study.chartservice.chart.infrastructure.WeekOfStockRepository;
import study.chartservice.chart.infrastructure.YearOfStockRepository;
import study.chartservice.common.KisUrls;
import study.chartservice.common.StockIndex;
import study.chartservice.common.StockRankOrder;
import study.chartservice.kis.dto.resp.FluctuationRankDataDto;
import study.chartservice.kis.dto.resp.IndexDataDto;
import study.chartservice.kis.dto.resp.InvestorDataDto;
import study.chartservice.kis.dto.resp.StockAskingPriceDataDto;
import study.chartservice.kis.dto.resp.StockDataDto;
import study.chartservice.kis.dto.resp.StockTimeDataDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class KisSchedulerServiceImp implements KisSchedulerService {

	private static final String DAY = "D";
	private static final String WEEK = "W";
	private static final String MONTH = "M";
	private static final String YEAR = "Y";
	private static final Integer LAST_DAY = 1;
	private static final Integer TIME_SPLIT = 8;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final KisApiService kisApiService;
	private final CompanyInfoRepository companyInfoRepository;
	private final DayOfStockRepository dayOfStockRepository;
	private final WeekOfStockRepository weekOfStockRepository;
	private final MonthOfStockRepository monthOfStockRepository;
	private final YearOfStockRepository yearOfStockRepository;
	private final InvestorRepository investorRepository;
	private final FluctuationRankRepository fluctuationRankRepository;
	private final MinOfStockRepository minOfStockRepository;
	private final IndexOfStockRepository indexOfStockRepository;
	private final StockAskingPriceRepository stockAskingPriceRepository;

	@Value("${kis.realApp.key}")
	private String appKey;

	@Value("${kis.realApp.secret}")
	private String appSecret;


	@Override
	@Transactional
//	@Scheduled(cron = "0 0/30 9-15 * * MON-FRI")    // 9~16시 사이에 30분마다 호출
	@Scheduled(cron = "0 12 14 * * MON-FRI")    // 9~16시 사이에 30분마다 호출
	public void collectKisDatOfTime() {
		log.info("collectKisDatOfTime 스케줄러 실행");
		List<CompanyInfo> companyInfos = companyInfoRepository.findAll();
		String minOfStockCreatAt = LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm00"));

		List<String> idList = minOfStockRepository.findIdsAll();

		companyInfos.forEach(companyInfo -> {
			UriComponentsBuilder chartTimeUriBuilder = createChartTimeUriBuilder();
			chartTimeUriBuilder
					.queryParam("FID_INPUT_ISCD", companyInfo.getStockCode())
					.queryParam("FID_INPUT_HOUR_1", minOfStockCreatAt.substring(TIME_SPLIT));

			try {
				// 한국 투자 증권 API 호출하여 하나의 종목 당 분봉 30개 중 제일 최신 데이터만 저장
				StockTimeDataDto stockTimeDataDto = fetchAndExtractChartTimeData(
						chartTimeUriBuilder);

				if (stockTimeDataDto == null) {
					Thread.sleep(50);
					return;
				}

				MinOfStock minOfStock = MinOfStock.builder()
						.stockCode(companyInfo.getStockCode())
						.stockCreatAt(minOfStockCreatAt)
						.prdy_vrss(stockTimeDataDto.getPrdy_vrss())
						.prdy_vrss_sign(stockTimeDataDto.getPrdy_vrss_sign())
						.prdy_ctrt(stockTimeDataDto.getPrdy_ctrt())
						.stck_prdy_clpr(stockTimeDataDto.getStck_prdy_clpr())
						.acml_vol(stockTimeDataDto.getAcml_vol())
						.acml_tr_pbmn(stockTimeDataDto.getAcml_tr_pbmn())
						.hts_kor_isnm(stockTimeDataDto.getHts_kor_isnm())
						.stck_prpr(stockTimeDataDto.getStck_prpr())
						.build();

				minOfStockRepository.save(minOfStock);

				Thread.sleep(50);
			} catch (JsonProcessingException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		List<String> ids = idList.stream()
				.map(KisSchedulerServiceImp::parseObjectId)
				.toList();

		minOfStockRepository.deleteAllById(ids);
		log.info("collectKisDatOfTime 스케줄러 종료");
	}


	@Override
	@Transactional
	@Scheduled(cron = "0 0 17 * * MON-FRI")    // 평일 17시 호출
	public void collectKisDataOfDay() {
		log.info("collectKisDataOfDay 스케줄러 실행");
		String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		List<CompanyInfo> companyInfos = companyInfoRepository.findAll();

		companyInfos.forEach(companyInfo -> {
			UriComponentsBuilder uriComponentsBuilder = createChartUriBuilder(nowDate, nowDate);
			uriComponentsBuilder.queryParam("FID_INPUT_ISCD", companyInfo.getStockCode());
			uriComponentsBuilder.queryParam("FID_PERIOD_DIV_CODE", DAY);

			try {
				List<DayOfStock> dayOfStocks = fetchAndExtractStockData(uriComponentsBuilder)
						.stream()
						.filter(data -> data.getStck_bsop_date() != null) // 데이터가 없는 경우 제외
						.filter(data -> data.getStck_bsop_date().equals(nowDate))
						.map(data -> DayOfStock.builder()
								.stockCode(companyInfo.getStockCode())
								.stck_bsop_date(data.getStck_bsop_date())
								.stck_oprc(data.getStck_oprc())
								.stck_hgpr(data.getStck_hgpr())
								.stck_lwpr(data.getStck_lwpr())
								.stck_clpr(data.getStck_clpr())
								.acml_vol(data.getAcml_vol())
								.acml_tr_pbmn(data.getAcml_tr_pbmn())
								.prdy_vrss(data.getPrdy_vrss())
								.prdy_vrss_sign(data.getPrdy_vrss_sign())
								.build())
						.toList();

				dayOfStockRepository.saveAll(dayOfStocks);
				// 한국 투자 증권 API 연속 호출 방지
				Thread.sleep(50);
			} catch (JsonProcessingException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		log.info("collectKisDataOfDay 스케줄러 종료");
	}

	@Override
	@Transactional
	@Scheduled(cron = "0 0 18 * * FRI")    // 매주 금요일 18시 호출
	public void collectKisDataOfWeek() {
		log.info("collectKisDataOfWeek 스케줄러 실행");

		LocalDate nowDate = LocalDate.now();
		int year = nowDate.getYear();
		int month = nowDate.getMonthValue();
		int day = nowDate.getDayOfMonth() - 1;

		String weekMonDays = getWeekMonDays(
				year + String.format("%02d", month) + String.format("%02d", day));

		String endDate = nowDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

		List<CompanyInfo> companyInfos = companyInfoRepository.findAll();

		System.out.println("companyInfos = " + companyInfos.size());

		companyInfos.forEach(companyInfo -> {
			UriComponentsBuilder uriComponentsBuilder = createChartUriBuilder(weekMonDays, endDate);
			uriComponentsBuilder.queryParam("FID_INPUT_ISCD", companyInfo.getStockCode());
			uriComponentsBuilder.queryParam("FID_PERIOD_DIV_CODE", WEEK);

			try {
				List<WeekOfStock> weekOfStocks = fetchAndExtractStockData(uriComponentsBuilder)
						.stream()
						.filter(data -> data.getStck_bsop_date() != null) // 데이터가 없는 경우 제외
						.map(data -> WeekOfStock.builder()
								.stockCode(companyInfo.getStockCode())
								.stck_bsop_date(data.getStck_bsop_date())
								.stck_oprc(data.getStck_oprc())
								.stck_hgpr(data.getStck_hgpr())
								.stck_lwpr(data.getStck_lwpr())
								.stck_clpr(data.getStck_clpr())
								.acml_vol(data.getAcml_vol())
								.acml_tr_pbmn(data.getAcml_tr_pbmn())
								.prdy_vrss(data.getPrdy_vrss())
								.prdy_vrss_sign(data.getPrdy_vrss_sign())
								.build())
						.toList();

				weekOfStockRepository.saveAll(weekOfStocks);
				// 한국 투자 증권 API 연속 호출 방지
				Thread.sleep(50);
			} catch (JsonProcessingException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		log.info("collectKisDataOfWeek 스케줄러 종료");
	}

	@Override
	@Transactional
	@Scheduled(cron = "0 0 5 1 * ?")    // 매월 1일 5시 호출
	public void collectKisDataOfMonth() {
		log.info("collectKisDataOfMonth 스케줄러 실행");
		LocalDate nowDate = LocalDate.now();
		int year = nowDate.getYear();
		int lastMonth = nowDate.getMonthValue() - 1;

		List<CompanyInfo> companyInfos = companyInfoRepository.findAll();

		companyInfos.forEach(companyInfo -> {
			UriComponentsBuilder uriComponentsBuilder = createChartUriBuilder(
					year + String.format("%02d", lastMonth) + "01",
					year + String.format("%02d", lastMonth) + "31");
			uriComponentsBuilder.queryParam("FID_INPUT_ISCD", companyInfo.getStockCode());
			uriComponentsBuilder.queryParam("FID_PERIOD_DIV_CODE", MONTH);

			try {
				List<MonthOfStock> monthOfStocks = fetchAndExtractStockData(uriComponentsBuilder)
						.stream()
						.filter(data -> data.getStck_bsop_date() != null) // 데이터가 없는 경우 제외
						.map(data -> MonthOfStock.builder()
								.stockCode(companyInfo.getStockCode())
								.stck_bsop_date(data.getStck_bsop_date())
								.stck_oprc(data.getStck_oprc())
								.stck_hgpr(data.getStck_hgpr())
								.stck_lwpr(data.getStck_lwpr())
								.stck_clpr(data.getStck_clpr())
								.acml_vol(data.getAcml_vol())
								.acml_tr_pbmn(data.getAcml_tr_pbmn())
								.prdy_vrss(data.getPrdy_vrss())
								.prdy_vrss_sign(data.getPrdy_vrss_sign())
								.build())
						.toList();

				monthOfStockRepository.saveAll(monthOfStocks);
				// 한국 투자 증권 API 연속 호출 방지
				Thread.sleep(50);
			} catch (JsonProcessingException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		log.info("collectKisDataOfMonth 스케줄러 종료");
	}

	@Override
	@Transactional
	@Scheduled(cron = "0 0 4 1 1 *")    // 매년 1월 1일 4시 호출
	public void collectKisDataOfYear() {
		log.info("collectKisDataOfYear 스케줄러 실행");
		int lastYear = LocalDate.now().getYear() - 1;

		List<CompanyInfo> companyInfos = companyInfoRepository.findAll();

		companyInfos.forEach(companyInfo -> {
			UriComponentsBuilder uriComponentsBuilder = createChartUriBuilder(
					lastYear + "0101", lastYear + "1231");
			uriComponentsBuilder.queryParam("FID_INPUT_ISCD", companyInfo.getStockCode());
			uriComponentsBuilder.queryParam("FID_PERIOD_DIV_CODE", YEAR);

			try {
				List<YearOfStock> yearOfStocks = fetchAndExtractStockData(uriComponentsBuilder)
						.stream()
						.filter(data -> data.getStck_bsop_date() != null) // 데이터가 없는 경우 제외
						.map(data -> YearOfStock.builder()
								.stockCode(companyInfo.getStockCode())
								.stck_bsop_date(data.getStck_bsop_date())
								.stck_oprc(data.getStck_oprc())
								.stck_hgpr(data.getStck_hgpr())
								.stck_lwpr(data.getStck_lwpr())
								.stck_clpr(data.getStck_clpr())
								.acml_vol(data.getAcml_vol())
								.acml_tr_pbmn(data.getAcml_tr_pbmn())
								.prdy_vrss(data.getPrdy_vrss())
								.prdy_vrss_sign(data.getPrdy_vrss_sign())
								.build())
						.toList();

				yearOfStockRepository.saveAll(yearOfStocks);
				// 한국 투자 증권 API 연속 호출 방지
				Thread.sleep(50);
			} catch (JsonProcessingException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		log.info("collectKisDataOfYear 스케줄러 종료");
	}

	@Override
	@Transactional
	@Scheduled(cron = "0 0 7 * * MON-FRI")    // 평일 7시 호출
	public void collectInvestor() {
		log.info("collectInvestor 스케줄러 실행");

		List<CompanyInfo> companyInfos = companyInfoRepository.findAll();

		companyInfos.forEach(companyInfo -> {
			UriComponentsBuilder builder = createInvestorUriBuilder(companyInfo.getStockCode());

			try {
				List<InvestorDataDto> investorDataDtos = fetchAndExtractInvestorData(builder);

				// 해당 날짜의 지난날 투자자 정보가 없을 경우
				if (investorDataDtos.size() < 2) {
					return;
				}

				// 해당 날짜의 전 거래일 투자자 정보만 get
				InvestorDataDto investorDataDto = investorDataDtos.get(LAST_DAY);

				investorRepository.save(Investor.builder()
						.stockCode(companyInfo.getStockCode())
						.stck_bsop_date(investorDataDto.getStck_bsop_date())
						.prsn_ntby_qty(investorDataDto.getPrsn_ntby_qty())
						.frgn_ntby_qty(investorDataDto.getFrgn_ntby_qty())
						.orgn_ntby_qty(investorDataDto.getOrgn_ntby_qty())
						.prdy_vrss_sign(investorDataDto.getPrdy_vrss_sign())
						.build());

				// 한국 투자 증권 API 연속 호출 방지
				Thread.sleep(50);

			} catch (JsonProcessingException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		log.info("collectInvestor 스케줄러 종료");
	}

	@Override
	@Transactional
	@Scheduled(cron = "0 0/20 9-15 * * MON-FRI")    // 9~16시 사이에 20분마다 호출
	public void collectFluctuationRank() {
		log.info("collectFluctuationRank 스케줄러 실행");

		// 기존 데이터 삭제 후 새로운 데이터 저장
		fluctuationRankRepository.deleteAll();

		List.of(StockRankOrder.INCREASE, StockRankOrder.DECREASE).forEach(rankSortClsCode -> {
			try {

				UriComponentsBuilder builder = createFluctuationRankUriBuilder()
						.queryParam("FID_RANK_SORT_CLS_CODE", rankSortClsCode.getValue());

				// 한국 투자 증권 API 호출
				List<FluctuationRank> fluctuationRanks = fetchAndExtractFluctuationRankData(
						builder).stream()
						.map(FluctuationRankDataDto -> FluctuationRank.builder()
								.stockCode(FluctuationRankDataDto.getStck_shrn_iscd())
								.rankStatus(rankSortClsCode.name())
								.data_rank(Integer.parseInt(FluctuationRankDataDto.getData_rank()))
								.hts_kor_isnm(FluctuationRankDataDto.getHts_kor_isnm())
								.stck_prpr(FluctuationRankDataDto.getStck_prpr())
								.prdy_vrss(FluctuationRankDataDto.getPrdy_vrss())
								.prdy_ctrt(FluctuationRankDataDto.getPrdy_ctrt())
								.prdy_vrss_sign(FluctuationRankDataDto.getPrdy_vrss_sign())
								.build())
						.toList();

				fluctuationRankRepository.saveAll(fluctuationRanks);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		});
		log.info("collectFluctuationRank 스케줄러 종료");
	}

	@Override
	@Transactional
	@Scheduled(cron = "0 0/25 9-15 * * MON-FRI")    // 9~16시 사이에 25분마다 호출
	public void collectKisDataOfIndex() {
		log.info("collectKisDataOfIndex 스케쥴러 실행");

		String nowDateTime = LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

		indexOfStockRepository.deleteAll();

		List.of(StockIndex.KOSPI, StockIndex.KOSDAQ).forEach(stockIndex -> {
			try {
				UriComponentsBuilder chartIndexUriBuilder = createChartIndexUriBuilder();
				chartIndexUriBuilder.queryParam("FID_INPUT_ISCD", stockIndex.getValue());

				IndexDataDto indexDataDto = fetchAndExtractIndexData(chartIndexUriBuilder);

				indexOfStockRepository.save(IndexOfStock.builder()
						.iscd(stockIndex.name())
						.dateTime(nowDateTime)
						.bstp_nmix_prpr(indexDataDto.getBstp_nmix_prpr())
						.bstp_nmix_prdy_vrss(indexDataDto.getBstp_nmix_prdy_vrss())
						.prdy_vrss_sign(indexDataDto.getPrdy_vrss_sign())
						.bstp_nmix_prdy_ctrt(indexDataDto.getBstp_nmix_prdy_ctrt())
						.build());

			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		});

		log.info("collectKisDataOfIndex 스케쥴러 종료");
	}

	@Override
	@Transactional
	@Scheduled(cron = "0 0/30 9-15 * * MON-FRI")    // 9~16시 사이에 30분마다 호출
	public void collectStockAskingPrice() {
		log.info("collectStockAskingPrice 스케쥴러 실행");
		List<CompanyInfo> companyInfos = companyInfoRepository.findAll();

		List<String> idList = stockAskingPriceRepository.findIdsAll();

		companyInfos.forEach(companyInfo -> {

			try {
				UriComponentsBuilder stockAskingPriceUriBuilder = createStockAskingPriceUriBuilder();
				stockAskingPriceUriBuilder.queryParam("FID_INPUT_ISCD", companyInfo.getStockCode());

				StockAskingPriceDataDto dto = fetchAndExtractStockAskingPriceData(
						stockAskingPriceUriBuilder);

				stockAskingPriceRepository.save(StockAskingPrice.builder()
						.stockCode(companyInfo.getStockCode())
						.aspr_acpt_hour(dto.getAspr_acpt_hour())
						.askp1(dto.getAskp1())
						.askp2(dto.getAskp2())
						.askp3(dto.getAskp3())
						.askp_rsqn1(dto.getAskp_rsqn1())
						.askp_rsqn2(dto.getAskp_rsqn2())
						.askp_rsqn3(dto.getAskp_rsqn3())
						.bidp1(dto.getBidp1())
						.bidp2(dto.getBidp2())
						.bidp3(dto.getBidp3())
						.bidp_rsqn1(dto.getBidp_rsqn1())
						.bidp_rsqn2(dto.getBidp_rsqn2())
						.bidp_rsqn3(dto.getBidp_rsqn3())
						.total_askp_rsqn(dto.getTotal_askp_rsqn())
						.total_bidp_rsqn(dto.getTotal_bidp_rsqn())
						.build());

				Thread.sleep(50);
			} catch (JsonProcessingException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		List<String> ids = idList.stream()
				.map(KisSchedulerServiceImp::parseObjectId)
				.toList();

		stockAskingPriceRepository.deleteAllById(ids);

		log.info("collectStockAskingPrice 스케쥴러 종료");
	}

	private UriComponentsBuilder createStockAskingPriceUriBuilder() {
		return UriComponentsBuilder.fromUriString(KisUrls.STOCK_ASKING_PRICE_PATH.getFullUrl())
				.queryParam("FID_COND_MRKT_DIV_CODE", "J");
	}

	private UriComponentsBuilder createChartIndexUriBuilder() {
		return UriComponentsBuilder.fromUriString(KisUrls.ITEM_CHART_PRICE_TIME_PATH.getFullUrl())
				.queryParam("FID_COND_MRKT_DIV_CODE", "U");
	}

	private UriComponentsBuilder createChartTimeUriBuilder() {
		return UriComponentsBuilder.fromUriString(KisUrls.ITEM_CHART_PRICE_TIME_PATH.getFullUrl())
				.queryParam("FID_ETC_CLS_CODE", "")
				.queryParam("FID_COND_MRKT_DIV_CODE", "J")
				.queryParam("FID_PW_DATA_INCU_YN", "N");
	}

	private UriComponentsBuilder createFluctuationRankUriBuilder() {
		return UriComponentsBuilder.fromHttpUrl(
						KisUrls.STOCK_FLUCTUATION_RANK_PATH.getFullUrl())
				.queryParam("FID_RSFL_RATE2", "")
				.queryParam("FID_COND_MRKT_DIV_CODE", "J")
				.queryParam("FID_COND_SCR_DIV_CODE", "20170")
				.queryParam("FID_INPUT_ISCD", "0000")
				.queryParam("FID_INPUT_CNT_1", "")
				.queryParam("FID_PRC_CLS_CODE", "1")
				.queryParam("FID_INPUT_PRICE_1", "")
				.queryParam("FID_INPUT_PRICE_2", "")
				.queryParam("FID_VOL_CNT", "")
				.queryParam("FID_TRGT_CLS_CODE", "0")
				.queryParam("FID_TRGT_EXLS_CLS_CODE", "0")
				.queryParam("FID_DIV_CLS_CODE", "0")
				.queryParam("FID_RSFL_RATE1", "");
	}

	private UriComponentsBuilder createInvestorUriBuilder(String stockCode) {
		return UriComponentsBuilder.fromHttpUrl(KisUrls.STOCK_PRICE_INVESTOR_PATH.getFullUrl())
				.queryParam("FID_COND_MRKT_DIV_CODE", "J")
				.queryParam("FID_INPUT_ISCD", stockCode);
	}

	private UriComponentsBuilder createChartUriBuilder(String startDate, String endDate) {
		return UriComponentsBuilder.fromHttpUrl(KisUrls.ITEM_CHART_PRICE_PATH.getFullUrl())
				.queryParam("FID_COND_MRKT_DIV_CODE", "J")
				.queryParam("FID_INPUT_DATE_1", startDate)
				.queryParam("FID_INPUT_DATE_2", endDate)
				.queryParam("FID_ORG_ADJ_PRC", "0");
	}

	private HttpHeaders createStockAskingPriceHeaders() throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization",
				"Bearer " + kisApiService.getKisAccessToken().getAccess_token());
		headers.set("appkey", appKey);
		headers.set("appsecret", appSecret);
		headers.set("tr_id", "FHKST01010200");
		headers.set("custtype", "P");
		return headers;
	}

	private HttpHeaders createChartIndexHeaders() throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization",
				"Bearer " + kisApiService.getKisAccessToken().getAccess_token());
		headers.set("appkey", appKey);
		headers.set("appsecret", appSecret);
		headers.set("tr_id", "FHPUP02100000");
		headers.set("custtype", "P");
		return headers;
	}

	private HttpHeaders createChartTimeHeaders() throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization",
				"Bearer " + kisApiService.getKisAccessToken().getAccess_token());
		headers.set("appkey", appKey);
		headers.set("appsecret", appSecret);
		headers.set("tr_id", "FHKST03010200");
		headers.set("custtype", "P");
		return headers;
	}

	private HttpHeaders createFluctuationRankHeaders() throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization",
				"Bearer " + kisApiService.getKisAccessToken().getAccess_token());
		headers.set("appkey", appKey);
		headers.set("appsecret", appSecret);
		headers.set("tr_id", "FHPST01700000");
		headers.set("custtype", "P");
		return headers;
	}

	private HttpHeaders createInvestorHeaders() throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization",
				"Bearer " + kisApiService.getKisAccessToken().getAccess_token());
		headers.set("appkey", appKey);
		headers.set("appsecret", appSecret);
		headers.set("tr_id", "FHKST01010900");
		headers.set("custtype", "P");
		return headers;
	}

	private HttpHeaders createChartHeaders() throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("authorization",
				"Bearer " + kisApiService.getKisAccessToken().getAccess_token());
		headers.set("appkey", appKey);
		headers.set("appsecret", appSecret);
		headers.set("tr_id", "FHKST03010100");
		headers.set("custtype", "P");
		return headers;
	}

	private StockAskingPriceDataDto fetchAndExtractStockAskingPriceData(
			UriComponentsBuilder builder)
			throws JsonProcessingException {
		// 한국 투자 증권 API 호출
		ResponseEntity<String> response = restTemplate.exchange(
				builder.toUriString(),
				HttpMethod.GET,
				new HttpEntity<>(createStockAskingPriceHeaders()),
				String.class);

		// API 응답 데이터 JSON 파싱
		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		return objectMapper.convertValue(jsonNode.path("output1"), new TypeReference<>() {
		});
	}

	private IndexDataDto fetchAndExtractIndexData(UriComponentsBuilder builder)
			throws JsonProcessingException {
		// 한국 투자 증권 API 호출
		ResponseEntity<String> response = restTemplate.exchange(
				builder.toUriString(),
				HttpMethod.GET,
				new HttpEntity<>(createChartIndexHeaders()),
				String.class);

		// API 응답 데이터 JSON 파싱
		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		return objectMapper.convertValue(jsonNode.path("output"), new TypeReference<>() {
		});
	}

	// 투자자 데이터 추출
	private List<InvestorDataDto> fetchAndExtractInvestorData(UriComponentsBuilder builder)
			throws JsonProcessingException {
		// 한국 투자 증권 API 호출
		ResponseEntity<String> response = restTemplate.exchange(
				builder.toUriString(),
				HttpMethod.GET,
				new HttpEntity<>(createInvestorHeaders()),
				String.class);

		// API 응답 데이터 JSON 파싱
		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		return objectMapper.convertValue(jsonNode.path("output"), new TypeReference<>() {
		});
	}

	private StockTimeDataDto fetchAndExtractChartTimeData(UriComponentsBuilder builder)
			throws JsonProcessingException {
		// 한국 투자 증권 API 호출
		ResponseEntity<String> response = restTemplate.exchange(
				builder.toUriString(),
				HttpMethod.GET,
				new HttpEntity<>(createChartTimeHeaders()),
				String.class);

		// API 응답 데이터 JSON 파싱
		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		return objectMapper.convertValue(jsonNode.path("output1"), new TypeReference<>() {
		});
	}

	// 주식 데이터 추출

	private List<StockDataDto> fetchAndExtractStockData(UriComponentsBuilder builder)
			throws JsonProcessingException {
		// 한국 투자 증권 API 호출
		ResponseEntity<String> response = restTemplate.exchange(
				builder.toUriString(),
				HttpMethod.GET,
				new HttpEntity<>(createChartHeaders()),
				String.class);

		// API 응답 데이터 JSON 파싱
		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		return objectMapper.convertValue(jsonNode.path("output2"), new TypeReference<>() {
		});
	}

	// 등락률 순위 데이터 추출
	private List<FluctuationRankDataDto> fetchAndExtractFluctuationRankData(
			UriComponentsBuilder builder)
			throws JsonProcessingException {
		ResponseEntity<String> response = restTemplate.exchange(
				builder.toUriString(),
				HttpMethod.GET,
				new HttpEntity<>(createFluctuationRankHeaders()),
				String.class);

		// API 응답 데이터 JSON 파싱
		JsonNode jsonNode = objectMapper.readTree(response.getBody());
		return objectMapper.convertValue(jsonNode.path("output"), new TypeReference<>() {
		});
	}

	// 해당 날짜 주의 월요일 날짜 구하기
	private String getWeekMonDays(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		return LocalDate.parse(date, formatter)
				.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
				.format(formatter);
	}

	// Mongodb id -> ObjectId 추출
	private static String parseObjectId(String objectId) {
		// ObjectId의 시작 위치를 찾음
		int startIndex = objectId.indexOf("\"$oid\": \"") + 9;

		// ObjectId의 끝 위치를 찾음
		int endIndex = objectId.indexOf("\"", startIndex);

		// ObjectId를 추출
		return objectId.substring(startIndex, endIndex);
	}
}
