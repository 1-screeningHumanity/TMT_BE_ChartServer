package study.chartservice.chart.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import study.chartservice.chart.domain.CompanyInfo;

public interface CompanyInfoRepository extends MongoRepository<CompanyInfo, String> {
	Optional<CompanyInfo> findByStockCode(String stockCode);
}
