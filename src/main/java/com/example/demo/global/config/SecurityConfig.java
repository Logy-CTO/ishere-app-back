package com.example.demo.global.config;

import com.example.demo.global.security.filter.TokenAuthenticationFilter;
import com.example.demo.global.security.token.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // AuthToken 클래스를 주입받음
    private final AuthToken jwtUtil;

    @Autowired
    public SecurityConfig(AuthToken jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // AuthenticationManager 빈을 설정
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // BCryptPasswordEncoder 빈을 설정
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HTTP 보안 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 보호 비활성화
                .formLogin().disable() // 폼 기반 로그인 비활성화
                .httpBasic().disable() // HTTP 기본 인증 비활성화
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/login", "/", "/join",
                                "/smstest.html","/user/login","/sendSMS","/verifySMS","/user/register","/session","/loginuser").permitAll() // 특정 URL 패턴은 모두 허용
                        .antMatchers("/admin").hasRole("ADMIN") // /admin URL은 ADMIN 역할을 가진 사용자에게만 허용
                        .anyRequest().authenticated() // 나머지 모든 요청은 인증된 사용자에게만 허용
                )
                .addFilterBefore(new TokenAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class) // TokenAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 관리 정책을 설정하여 세션을 사용하지 않음
    }
}
