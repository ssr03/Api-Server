package framework.apiserver.core.schedule;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Schedule {
    private final ScheduleService scheduleService;

    /**
     * schedule job1
     * Desc. 매일 오전 6시 30분
     **/
    @Scheduled(cron = "0 50 9 * * ?")
    public void cronJobMailing(){

    }

    /**
     * schedule job2
     * 1시간 간격 데이터 수집
     **/
    @Scheduled(fixedRate = 60*60*1000)
    public void getDataCollection(){

    }
}
