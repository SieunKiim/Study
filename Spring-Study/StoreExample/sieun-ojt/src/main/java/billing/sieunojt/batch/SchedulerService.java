package billing.sieunojt.batch;

import billing.sieunojt.batch.config.ExpireJobConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerService {
    private final JobLauncher jobLauncher;
    private final ExpireJobConfiguration expireJobConfiguration;

    @Scheduled(fixedDelay = 1000 * 5)
    public void test(){
        System.out.println("test!");
        Map<String, JobParameter> configMap = new HashMap<>();
        configMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameter = new JobParameters(configMap);

        try {
            jobLauncher.run(expireJobConfiguration.expireJob(), jobParameter);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

}
