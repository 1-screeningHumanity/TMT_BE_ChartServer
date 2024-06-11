package study.chartservice.chart.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import study.chartservice.chart.domain.MinOfStock;

public interface MinOfStockRepository extends MongoRepository<MinOfStock, String> {
	@Query(value = "{}", fields = "{ '_id' : 1 }")
	List<String> findIdsAll();

	Optional<MinOfStock> findByStockCode(String stockCode);
}
