package com.ecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.example.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

    @Configuration
    @EnableMethodSecurity(prePostEnabled = true)

    //Configures JWT-based stateless security, allowing public endpoints and securing all others using a custom filter.
    public class SecurityConfig {

        @Bean
        public JwtTokenFilter jwtTokenFilter() {
            return new JwtTokenFilter();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/public/**").permitAll() // Allow unauthenticated access to /public
                            .anyRequest().authenticated() // Require auth for all other endpoints
                    )
                    .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

        // Optional: expose AuthenticationManager if needed (e.g., for login)
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }

}
