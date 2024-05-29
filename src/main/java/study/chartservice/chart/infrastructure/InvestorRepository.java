package study.chartservice.chart.infrastructure;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import study.chartservice.chart.domain.Investor;

public interface InvestorRepository extends MongoRepository<Investor, String> {
	@Query(value = "{ 'stockCode' : ?0 }", sort = "{ 'stck_bsop_date' : -1 }")
	List<Investor> findTopTenByStockCodeDesc(String stockCode, Pageable pageable);
}
