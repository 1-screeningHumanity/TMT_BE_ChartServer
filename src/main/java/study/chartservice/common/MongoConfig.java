package study.chartservice.common;

import com.mongodb.TransactionOptions;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class MongoConfig {
	@Bean
	public MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDbFactory) {
		TransactionOptions options = TransactionOptions.builder()
				.maxCommitTime(10L, TimeUnit.MINUTES)
				.build();

		return new MongoTransactionManager(mongoDbFactory, options);
	}

}
