package study.chartservice.chart.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import study.chartservice.chart.domain.Investor;

public interface InvestorRepository extends MongoRepository<Investor, String> {
	Optional<Investor> findByStockCode(String stockCode);
}
