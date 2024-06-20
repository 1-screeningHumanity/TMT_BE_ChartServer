package study.chartservice.chart.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.chartservice.chart.dto.resp.StockDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockVo {
	private String id;  //	주식 id
	private String stockCode;   //	주식 코드
	private String stck_bsop_date;  //	주식 영업 일자
	private String stck_clpr;   //	주식 종가
	private String stck_oprc;   //	주식 시가
	private String stck_hgpr;   //	주식 최고가
	private String stck_lwpr;   //	주식 최저가
	private String acml_vol;    //	누적 거래량
	private String acml_tr_pbmn;    //	누적 거래 대금
	private String prdy_vrss;   //	전일 대비
	private String prdy_vrss_sign;  //	전일 대비 부호

	public static StockVo getStockDto(StockDto dto) {
		return StockVo.builder()
				.id(dto.getId())
				.stockCode(dto.getStockCode())
				.stck_bsop_date(dto.getStck_bsop_date())
				.stck_clpr(dto.getStck_clpr())
				.stck_oprc(dto.getStck_oprc())
				.stck_hgpr(dto.getStck_hgpr())
				.stck_lwpr(dto.getStck_lwpr())
				.acml_vol(dto.getAcml_vol())
				.acml_tr_pbmn(dto.getAcml_tr_pbmn())
				.prdy_vrss(dto.getPrdy_vrss())
				.prdy_vrss_sign(dto.getPrdy_vrss_sign())
				.build();
	}
}
