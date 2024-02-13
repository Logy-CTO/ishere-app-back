package com.example.demo.global.security.token;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 토큰을 나타내는 클래스입니다.
 * 주로 토큰의 생성, 유효성 검사, 클레임 추출 등과 관련된 기능을 제공합니다.
 * JWT 토큰은 사용자의 인증 정보와 추가적인 메타데이터를 포함하는 문자열입니다.
 * 이 클래스는 주로 JWT 토큰의 생성, 서명, 검증 등의 역할을 수행합니다.
 * 토큰의 생성에는 사용자의 ID, 권한(role), 만료 시간(expiry) 등의 정보가 포함될 수 있습니다.
 * validate() 메서드를 사용하여 토큰의 유효성을 검사할 수 있습니다.
 */


@Component
public class AuthToken {

    private SecretKey secretKey;

    // 주입된 시크릿 값을 이용하여 AuthToken 인스턴스를 생성하는 생성자
    public AuthToken(@Value("${spring.jwt.secret}")String secret) {
        // 시크릿 값을 사용하여 SecretKey를 생성합니다.
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // 주어진 토큰에서 사용자 이름을 추출하는 메서드
    public String getUsername(String token) {
        // 토큰을 파싱하여 사용자 이름(Claim)을 반환합니다.
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    // 주어진 토큰에서 사용자 역할을 추출하는 메서드
    public String getRole(String token) {
        // 토큰을 파싱하여 사용자 역할(Claim)을 반환합니다.
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // 주어진 토큰이 만료되었는지 확인하는 메서드
    public Boolean isExpired(String token) {
        // 토큰의 만료 시간을 현재 시간과 비교하여 만료 여부를 반환합니다.
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // 주어진 사용자 이름, 역할 및 만료 시간을 이용하여 JWT 토큰을 생성하는 메서드
    public String createJwt(String username, String role, Long expiredMs) {
        // JWT 토큰을 빌더를 사용하여 생성하고 반환합니다.
        return Jwts.builder()
                .claim("username", username) // 사용자 이름(Claim) 추가
                .claim("role", role) // 사용자 역할(Claim) 추가
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발급 시간 설정
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 토큰 만료 시간 설정
                .signWith(secretKey) // 시크릿 키로 서명
                .compact(); // 토큰을 문자열로 변환하여 반환
    }
}
