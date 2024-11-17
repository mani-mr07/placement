package com.placement.placement.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/students").permitAll()
                        .requestMatchers("/api/students/**").hasRole("STUDENT")
                        .requestMatchers("/api/staff/**").hasRole("STAFF")
                        .anyRequest().authenticated()
                );  // For simplicity, using HTTP Basic Authentication here

        return http.build();
    }
}
