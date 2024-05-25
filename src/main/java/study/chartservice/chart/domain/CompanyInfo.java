package study.chartservice.chart.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "company_info")
public class CompanyInfo {
	@Id
	private String id;
	private String name;
	private String stockCode;
	private String registerdAt;
}
