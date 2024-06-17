package study.chartservice.chart.infrastructure;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import study.chartservice.chart.domain.MonthOfStock;

public interface MonthOfStockRepository extends MongoRepository<MonthOfStock, String> {
	@Query(sort = "{ 'stck_bsop_date' : 1 }")
	List<MonthOfStock> findByStockCode(String stockCode);
}
