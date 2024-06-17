package study.chartservice.chart.infrastructure;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import study.chartservice.chart.domain.YearOfStock;

public interface YearOfStockRepository extends MongoRepository<YearOfStock, String> {
	@Query(sort = "{ 'stck_bsop_date' : 1 }")
	List<YearOfStock> findByStockCode(String stockCode);

}
