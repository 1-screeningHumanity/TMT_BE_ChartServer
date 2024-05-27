package study.chartservice.chart.infrastructure;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import study.chartservice.chart.domain.WeekOfStock;

public interface WeekOfStockRepository extends MongoRepository<WeekOfStock, String> {
	List<WeekOfStock> findByStockCode(String stockCode);

}
