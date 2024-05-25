package study.chartservice.chart.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import study.chartservice.chart.domain.YearOfStock;

public interface YearOfStockRepository extends MongoRepository<YearOfStock, String> {

}
