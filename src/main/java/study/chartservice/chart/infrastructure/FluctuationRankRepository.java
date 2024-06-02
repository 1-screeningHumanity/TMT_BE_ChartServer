package study.chartservice.chart.infrastructure;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import study.chartservice.chart.domain.FluctuationRank;

public interface FluctuationRankRepository extends MongoRepository<FluctuationRank, String> {

	@Query(value = "{'rankStatus': ?0 }", sort = "{ 'data_rank' : 1 }")
	List<FluctuationRank> findByRankStatus(String rankStatus);
}
