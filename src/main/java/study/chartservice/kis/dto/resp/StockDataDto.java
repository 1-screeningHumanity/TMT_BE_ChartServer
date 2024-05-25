package study.chartservice.kis.dto.resp;

import lombok.Getter;

@Getter
public class StockDataDto {
	private String stck_bsop_date;
	private String stck_clpr;
	private String stck_oprc;
	private String stck_hgpr;
	private String stck_lwpr;
	private String acml_vol;
	private String acml_tr_pbmn;
	private String flng_cls_code;
	private String prtt_rate;
	private String mod_yn;
	private String prdy_vrss_sign;
	private String prdy_vrss;
	private String revl_issu_reas;
}
