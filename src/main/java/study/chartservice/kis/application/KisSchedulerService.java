package study.chartservice.kis.application;

public interface KisSchedulerService {
	void collectKisDataOfDay();
	void collectKisDataOfWeek();
	void collectKisDataOfMonth();
	void collectKisDataOfYear();
	void collectInvestor();
	void collectFluctuationRank();
}
