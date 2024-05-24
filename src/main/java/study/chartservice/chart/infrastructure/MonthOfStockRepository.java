package study.chartservice.chart.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import study.chartservice.chart.domain.MonthOfStock;

public interface MonthOfStockRepository extends MongoRepository<MonthOfStock, String> {

}
