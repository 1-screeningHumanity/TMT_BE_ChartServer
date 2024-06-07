package study.chartservice.kis.dto.resp;

import lombok.Getter;

@Getter
public class StockTimeDataDto {
	private String stck_bsop_date;
	private String stck_cntg_hour;
	private String acml_tr_pbmn;
	private String stck_prpr;
	private String stck_oprc;
	private String stck_hgpr;
	private String stck_lwpr;
	private String cntg_vol;
}
