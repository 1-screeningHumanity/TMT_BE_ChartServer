package study.chartservice.chart.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.chartservice.chart.dto.resp.StockMinDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMinVo {
	private String stockCode;   // 주식 코드
	private String stockCreatAt; // 주식 영업 일자
	private String prdy_vrss;   // 	전일 대비
	private String prdy_vrss_sign;  //  전일 대비 부호
	private String prdy_ctrt;   // 전일 대비율
	private String stck_prdy_clpr;  //  주식 전일 종가
	private String acml_vol;    // 누적 거래량
	private String acml_tr_pbmn;    //  누적 거래 대금
	private String hts_kor_isnm;    // HTS 한글 종목명
	private String stck_prpr;   // 주식 현재가

	public static StockMinVo getStockMinDto(StockMinDto dto) {
		return StockMinVo.builder()
				.stockCode(dto.getStockCode())
				.stockCreatAt(dto.getStockCreatAt())
				.prdy_vrss(dto.getPrdy_vrss())
				.prdy_vrss_sign(dto.getPrdy_vrss_sign())
				.prdy_ctrt(dto.getPrdy_ctrt())
				.stck_prdy_clpr(dto.getStck_prdy_clpr())
				.acml_vol(dto.getAcml_vol())
				.acml_tr_pbmn(dto.getAcml_tr_pbmn())
				.hts_kor_isnm(dto.getHts_kor_isnm())
				.stck_prpr(dto.getStck_prpr())
				.build();
	}
}
