package com.Ecom_microservices.order_service.config;
// In your Cart Service application
import com.example.jwt.config.RequestContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.jsonwebtoken.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestTokenInterceptor() {
        return requestTemplate -> {
            String token = RequestContext.getToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
