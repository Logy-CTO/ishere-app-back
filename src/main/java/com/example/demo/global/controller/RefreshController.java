package com.example.demo.global.controller;

import com.example.demo.global.security.service.JwtService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RefreshController {
    private final JwtService jwtService;

    public RefreshController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> validateRefreshToken(@RequestBody HashMap<String, String> bodyJson){

        Map<String, String> map = jwtService.validateRefreshToken(bodyJson.get("refreshToken"));

        if(map.get("status").equals("402")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 만료되었습니다.");
        }

        // 수정: AccessToken을 Response Header에 추가
        String accessToken = map.get("accessToken");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", accessToken)
                .body("Refresh Token이 유효합니다.");
    }
}

