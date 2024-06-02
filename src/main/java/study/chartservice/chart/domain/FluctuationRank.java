package study.chartservice.chart.domain;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "fluctuationrank")
public class FluctuationRank {
	@Id
	private String id;
	private String dateTime;
	private String rankStatus;
	private String stockCode;
	private Integer data_rank;
	private String hts_kor_isnm;
	private String stck_prpr;
	private String prdy_vrss;
	private String prdy_ctrt;
	private String prdy_vrss_sign;

	@Builder
	public FluctuationRank(String rankStatus, String stockCode, Integer data_rank, String hts_kor_isnm,
			String stck_prpr, String prdy_vrss, String prdy_ctrt, String prdy_vrss_sign) {
		this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
		this.rankStatus = rankStatus;
		this.stockCode = stockCode;
		this.data_rank = data_rank;
		this.hts_kor_isnm = hts_kor_isnm;
		this.stck_prpr = stck_prpr;
		this.prdy_vrss = prdy_vrss;
		this.prdy_ctrt = prdy_ctrt;
		this.prdy_vrss_sign = prdy_vrss_sign;

	}
}
