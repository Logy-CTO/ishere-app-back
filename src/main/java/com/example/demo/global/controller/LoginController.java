package com.example.demo.global.controller;

import com.example.demo.global.dto.LoginDTO;
import com.example.demo.global.security.service.JWTUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {

    // JWTUserService 주입
    private final JWTUserService userService;

    @Autowired
    public LoginController(JWTUserService userService) {
        this.userService = userService;
    }

    // 로그인 요청 처리 메서드
    @PostMapping(value = "/loginuser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        String username = loginDTO.getUsername();
        String password = "울트라킹왕짱코딩의신택수";

        // 사용자 인증 및 토큰 생성
        String token = userService.authenticateAndGenerateToken(username, password);

        // 응답에 토큰 추가
        response.addHeader("Authorization", "Bearer " + token);

        // 클라이언트에게 응답 전송
        return ResponseEntity.ok(token);
    }
}
