package study.chartservice.kis.dto.resp;

import lombok.Getter;

@Getter
public class FluctuationRankDataDto {
	private String stck_shrn_iscd;
	private String data_rank;
	private String hts_kor_isnm;
	private String stck_prpr;
	private String prdy_vrss;
	private String prdy_vrss_sign;
	private String prdy_ctrt;
	private String acml_vol;
	private String stck_hgpr;
	private String hgpr_hour;
	private String acml_hgpr_date;
	private String stck_lwpr;
	private String lwpr_hour;
	private String acml_lwpr_date;
	private String lwpr_vrss_prpr_rate;
	private String dsgt_date_clpr_vrss_prpr_rate;
	private String cnnt_ascn_dynu;
	private String hgpr_vrss_prpr_rate;
	private String cnnt_down_dynu;
	private String oprc_vrss_prpr_sign;
	private String oprc_vrss_prpr;
	private String oprc_vrss_prpr_rate;
	private String prd_rsfl;
	private String prd_rsfl_rate;
}