package com.example.demo.global.controller;

import com.example.demo.global.security.service.TokenBlacklistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class LogoutController {

    private final TokenBlacklistService tokenBlacklistService;

    public LogoutController(TokenBlacklistService tokenBlacklistService) {
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/logoutuser")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        // Authorization 헤더에서 토큰 추출
        String accessToken = token.replace("Bearer ", "");
        // 블랙리스트에 토큰 추가
        tokenBlacklistService.addToBlacklist(accessToken, LocalDateTime.now());
        // 성공적으로 로그아웃 처리되었음을 응답
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}