package billing.sieunojt.common.config.openFeign;

import billing.sieunojt.openfeign.payletter.PayletterFeign;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = PayletterFeign.class)
public class PayletterFeignConfig {
    @Value("${pg.payletter.api-key-payment}")
    private String API_KEY_PAYMENT;

    @Bean
    public RequestInterceptor payletterRequestInterceptor() {

        return requestTemplate -> {
            if (requestTemplate.feignTarget().type() == PayletterFeign.class) {
                requestTemplate.header("Authorization", API_KEY_PAYMENT);
            }
        };
    }
}
