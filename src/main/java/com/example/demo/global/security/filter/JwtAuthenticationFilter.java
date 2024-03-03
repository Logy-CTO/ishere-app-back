package com.example.demo.global.security.filter;

import com.example.demo.global.security.service.TokenBlacklistService;
import com.example.demo.global.security.token.JwtTokenProvider;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

// OncePerRequestFilter를 상속한 TokenAuthenticationFilter 클래스입니다.
// OncePerRequestFilter는 한 번의 요청에 대해 단 한 번만 실행되는 필터를 만들기 위한 스프링 프레임워크의 추상 클래스입니다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 헤더에서 JWT를 받아옵니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        // 유효한 토큰인지 확인합니다.
        if (token != null) {
            // 토큰이 블랙리스트에 있는지 확인합니다.
            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 블랙리스트에 있어 유효하지 않습니다");
            } else {
                // 유효한 토큰이라면, 토큰으로부터 유저 정보를 받아옵니다.
                if (jwtTokenProvider.validateToken(token)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    // SecurityContext에 Authentication 객체를 저장합니다.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}