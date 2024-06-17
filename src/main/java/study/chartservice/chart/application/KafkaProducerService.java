package study.chartservice.chart.application;

public interface KafkaProducerService {
	void sendTrade(String message);
}
