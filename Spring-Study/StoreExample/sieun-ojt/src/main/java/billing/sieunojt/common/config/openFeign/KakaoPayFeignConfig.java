package billing.sieunojt.common.config.openFeign;

import billing.sieunojt.openfeign.kakaoPay.KakaoPayFeign;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {KakaoPayFeign.class})
public class KakaoPayFeignConfig {
    @Value("${pg.kakao-pay.admin-key}")
    private String SERVICE_APP_ADMIN_KEY;

    @Bean
    public RequestInterceptor kakoRequestInterceptor(){

        return requestTemplate -> {
            if (requestTemplate.feignTarget().type() == KakaoPayFeign.class) {
                requestTemplate.header("Authorization", SERVICE_APP_ADMIN_KEY);
            }
        };
    }
}
