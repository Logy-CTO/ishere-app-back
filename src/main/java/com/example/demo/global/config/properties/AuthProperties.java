package com.example.demo.global.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/* 보류 시크릿 키는 못 넘긴다는데 확실치 않음
@Data
@Configuration
@ConfigurationProperties("auth-properties")
public class AuthProperties {

    private JwtProperties jwtProperties = new JwtProperties();
    @Data
    public static class JwtProperties {
        @Value("${spring.jwt.secret}")
        private String secretKey;
    }
}*/