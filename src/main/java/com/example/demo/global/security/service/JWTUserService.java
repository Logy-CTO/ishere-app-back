package com.example.demo.global.security.service;

import com.example.demo.global.security.token.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

// JWTUserService 클래스는 사용자의 인증 및 토큰 생성을 담당하는 서비스 클래스이다.
@Service
public class JWTUserService {

    private final AuthenticationManager authenticationManager;
    private final AuthToken jwtUtil;

    // AuthenticationManager와 AuthToken을 주입받는 생성자
    @Autowired
    public JWTUserService(AuthenticationManager authenticationManager, AuthToken jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // 주어진 사용자 이름과 비밀번호로 사용자를 인증하고 JWT 토큰을 생성하여 반환하는 메서드
    public String authenticateAndGenerateToken(String username, String password) {
        // 사용자 인증을 수행하여 Authentication 객체를 가져옴
        Authentication authentication = authenticateUser(username, password);

        // 사용자의 권한(Role)을 가져옴
        String role = getUserRole(authentication);

        // JWT 토큰 생성
        return jwtUtil.createJwt(username, role, 30 * 24 * 60 * 60L); // 토큰의 만료 시간은 30일
    }

    // 주어진 사용자 이름과 비밀번호로 사용자를 인증하는 메서드
    private Authentication authenticateUser(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    // 인증된 사용자의 권한(Role)을 가져오는 메서드
    private String getUserRole(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        return auth.getAuthority();
    }
}

