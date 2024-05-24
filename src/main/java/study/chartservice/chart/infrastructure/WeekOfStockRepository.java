package study.chartservice.chart.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import study.chartservice.chart.domain.WeekOfStock;

public interface WeekOfStockRepository extends MongoRepository<WeekOfStock, String> {

}
