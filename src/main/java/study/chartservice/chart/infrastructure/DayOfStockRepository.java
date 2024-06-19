package study.chartservice.chart.infrastructure;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import study.chartservice.chart.domain.DayOfStock;

public interface DayOfStockRepository extends MongoRepository<DayOfStock, String> {
	@Query(sort = "{ 'stck_bsop_date' : 1 }")
	List<DayOfStock> findByStockCode(String stockCode);

}
