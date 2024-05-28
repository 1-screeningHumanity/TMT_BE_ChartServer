package study.chartservice.kis.application;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface KisSchedulerService {
	void collectKisDataOfDay();
	void collectKisDataOfWeek();
	void collectKisDataOfMonth();
	void collectKisDataOfYear();
	void collectInvestor();
}
