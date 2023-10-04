package billing.sieunojt.batch.config;

import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.repository.exchange.ExchangeRepository;
import billing.sieunojt.domain.exchange.service.ExchangeService;
import billing.sieunojt.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ExpireJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExchangeRepository exchangeRepository;
    private final ExchangeService exchangeService;
    private final UserService userService;

    private int chunkSize = 10;

    @Bean
    public Job expireJob() {
        return jobBuilderFactory.get("expireJob")
                .start(expireStep1())
                .build();
    }

    @Bean
    public Step expireStep1() {
        return stepBuilderFactory.get("checkExpire")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>> Exchange 만료 Batch");

                    List<Exchange> exchanges = exchangeRepository.findAllByExpiredAtBeforeAndLeftDiamondNot(LocalDateTime.now(), 0);

                    exchanges.forEach(exchange -> {
                        userService.withdrawDiamond(exchange.getUserId(), exchange.getLeftDiamond());
                        exchangeService.processExpiration(exchange, 0);
                    });
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
