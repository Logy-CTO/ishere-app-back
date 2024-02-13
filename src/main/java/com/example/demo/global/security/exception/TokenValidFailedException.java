package com.example.demo.global.security.exception;

// 토큰 유효성 검사에 실패한 경우 발생하는 예외 클래스입니다.
public class TokenValidFailedException extends RuntimeException {

    // 기본 생성자입니다. "Failed to generate Token." 메시지를 갖는 예외를 생성합니다.
    public TokenValidFailedException() {
        super("Failed to generate Token.");
    }

    // 메시지를 지정하는 생성자입니다. 주어진 메시지로 예외를 생성합니다.
    private TokenValidFailedException(String message) {
        super(message);
    }
}
