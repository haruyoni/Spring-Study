package com.miniproj.etc;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.miniproj.domain.PointLog;
import com.miniproj.persistence.PointLogDAO;

@Component
public class SampleScheduler {
	
	@Inject
	private PointLogDAO pdao;

//	@Scheduled(cron="0/5 * * * * *") // 0초에 5초마다 실행
	@Scheduled(cron="0 0/1 * * * *") // 1분마다 실행
//	@Scheduled(cron="0 17 10 * * *") // 매일 10시 17분에
//	@Scheduled(cron="* * * * * *") // 매초마다
	public void sampleSchedule() throws Exception {
		System.out.println("========================= scheduling =========================");
		pdao.insertPointLog(new PointLog(-1, null, "테스트", 1, "hayeoll"));
		
	}
	
}
