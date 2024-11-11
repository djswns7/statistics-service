package com.example.statisticsservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/public/**").permitAll() // 특정 경로에 대한 인증 비활성화
                                .requestMatchers("/call-records/**").permitAll() // 레코드 경로 테스트중
                                .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .formLogin(formlogin -> formlogin.disable()) // 기본 로그인 폼 활성화
                .csrf(csrf -> csrf.disable()); // CSRF 비활성화 (필요 시)

        return http.build();
    }
}