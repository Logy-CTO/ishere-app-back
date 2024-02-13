package com.example.demo.global.security.handler;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import lombok.RequiredArgsConstructor;

// 스프링 컴포넌트로 등록되는 클래스를 나타내는 어노테이션입니다.
@Component
// 필수 생성자를 생성하기 위한 롬복 어노테이션입니다.
@RequiredArgsConstructor
// TokenAccessDeniedHandler 클래스입니다.
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    // 예외 처리 해결자를 주입받습니다.
    private final HandlerExceptionResolver handlerExceptionResolver;

    // 접근 거부 예외를 처리하는 메서드입니다.
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        // 예외 처리 해결자를 사용하여 예외를 해결합니다.
        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
}