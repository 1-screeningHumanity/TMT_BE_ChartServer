package study.chartservice.chart.infrastructure;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import study.chartservice.chart.domain.WeekOfStock;

public interface WeekOfStockRepository extends MongoRepository<WeekOfStock, String> {
	@Query(sort = "{ 'stck_bsop_date' : 1 }")
	List<WeekOfStock> findByStockCode(String stockCode);

}
