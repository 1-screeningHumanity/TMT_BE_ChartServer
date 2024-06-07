package study.chartservice.chart.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import study.chartservice.chart.domain.IndexOfStock;

public interface IndexOfStockRepository extends MongoRepository<IndexOfStock, String> {
	Optional<IndexOfStock> findByIscd(String iscd);
}
