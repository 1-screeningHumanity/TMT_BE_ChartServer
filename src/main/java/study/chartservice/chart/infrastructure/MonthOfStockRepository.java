package study.chartservice.chart.infrastructure;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import study.chartservice.chart.domain.MonthOfStock;

public interface MonthOfStockRepository extends MongoRepository<MonthOfStock, String> {
	List<MonthOfStock> findByStockCode(String stockCode);
}
