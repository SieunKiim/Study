package billing.sieunojt.common.config.web;

import billing.sieunojt.common.interceptor.RemoteAddressInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://testpgapi.payletter.com", "http://127.0.0.1:8080", "https://testpg.payletter.com", "null")
                .allowedMethods("GET", "POST")
                .maxAge(3000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RemoteAddressInterceptor())
                .addPathPatterns("/exchange/refund");
    }
}
