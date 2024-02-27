package com.example.demo.global.controller;

import com.example.demo.global.entity.Token;
import com.example.demo.global.entity.UserEntity;
import com.example.demo.global.security.Repository.JWTUserRepository;
import com.example.demo.global.security.service.JwtService;
import com.example.demo.global.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    // 로그인 요청 처리 메서드
    private final JwtTokenProvider jwtTokenProvider;
    private final JWTUserRepository jwtUserRepository;
    private final JwtService jwtService;

    @PostMapping(value = "/loginuser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Token> login(@RequestBody Map<String, String> user) {
        log.info("PhoneNumber = {}", user.get("username"));
        UserEntity member = jwtUserRepository.findByUsername(user.get("username"));

        Token tokenDto = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRole());
        log.info("getrole = {}", member.getRole());
        jwtService.login(tokenDto);

        // 토큰을 헤더에 추가하여 응답
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + tokenDto.getAccessToken())
                .body(tokenDto);
    }

}
