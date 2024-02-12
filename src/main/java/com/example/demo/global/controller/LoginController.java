package com.example.demo.global.controller;

import com.example.demo.domain.User.SignUpDto;
import com.example.demo.global.JWT.JWTUtil;
import com.example.demo.global.dto.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/loginuser")
    public ResponseEntity<String> loginUser(@RequestBody JoinDTO joinDTO, HttpServletResponse response) {

            String username = joinDTO.getUsername();
            System.out.println(username);

            // 사용자 인증 <<-- 여기서 오류 나는데... 이유가 뭘까
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, "울트라킹왕짱코딩의신택수", null));

            // 사용자의 권한(Role) 가져오기
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
            GrantedAuthority auth = iterator.next();
            String role = auth.getAuthority();

            // 토큰 생성
            String token = jwtUtil.createJwt(username, role, 30 * 24 * 60 * 60L);

            // 응답에 토큰 추가
            response.addHeader("Authorization", "Bearer " + token);

            // 클라이언트에게 응답 전송
            return ResponseEntity.ok(token);

    }

}
