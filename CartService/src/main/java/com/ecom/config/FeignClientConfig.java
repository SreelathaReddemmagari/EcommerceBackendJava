package com.ecom.config;
// In your Cart Service application
import com.example.jwt.config.RequestContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.jsonwebtoken.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

//FeignClientConfig fetches the same JWT token and attaches it to the Feign request.
//Intercepts Feign requests to add the current JWT token from context into the Authorization header.
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