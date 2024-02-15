package com.example.demo.global.security.token;

import com.example.demo.global.entity.RefreshToken;
import com.example.demo.global.entity.Token;
import com.example.demo.global.security.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private SecretKey accessSecretKey;
    private SecretKey refreshSecretKey;

    private final CustomUserDetailsService customUserDetailsService;

    // 주입된 시크릿 값을 이용하여 Access Token과 Refresh Token의 SecretKey를 생성하는 생성자
    @Autowired
    public JwtTokenProvider(@Value("${spring.jwt.accessSecret}") String accessSecret,
                            @Value("${spring.jwt.refreshSecret}") String refreshSecret, CustomUserDetailsService customUserDetailsService) {
        // 시크릿 값을 사용하여 Access Token의 SecretKey를 생성합니다.
        this.accessSecretKey = new SecretKeySpec(accessSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        // 시크릿 값을 사용하여 Refresh Token의 SecretKey를 생성합니다.
        this.refreshSecretKey = new SecretKeySpec(refreshSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        this.customUserDetailsService = customUserDetailsService;
    }

    private long accessTokenValidTime = 10 * 60 * 1000L; // 10분(밀리초)
    private long refreshTokenValidTime = 30 * 60 * 1000L; // 30분(밀리초)

    //private long accessTokenValidTime = 30 * 60 * 1000L; // 30분(밀리초)
    //private long refreshTokenValidTime = 14 * 24 * 60 * 60 * 1000L; // 14일(밀리초)
    Date now = new Date();

    // 주어진 사용자 이름, 역할 및 만료 시간을 이용하여 JWT 토큰을 생성하는 메서드
    public Token createAccessToken(String username, String role) {
        // JWT 토큰을 빌더를 사용하여 생성하고 반환합니다.

        //Access Token
        String accessToken = Jwts.builder()
                .claim("username", username) // 사용자 이름(Claim) 추가
                .claim("role", role) // 사용자 역할(Claim) 추가
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 시크릿 키로 서명
                .compact(); // 토큰을 문자열로 변환하여 반환

        //Refresh Token
        String refreshToken =  Jwts.builder()
                .claim("username", username) // 사용자 이름(Claim) 추가
                .claim("role", role) // 사용자 역할(Claim) 추가
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)// 시크릿 키로 서명
                .compact(); // 토큰을 문자열로 변환하여 반환

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(username).build();
    }

    public String validateRefreshToken(RefreshToken refreshTokenObj){

        // refresh 객체에서 refreshToken 추출
        String refreshToken = refreshTokenObj.getRefreshToken();
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);
            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
            if (!claims.getBody().getExpiration().before(new Date())) {
                return recreationAccessToken(claims.getBody().get("username").toString(), claims.getBody().get("roles"));
            }
        }catch (Exception e) {
            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
            return null;

        }

        return null;
    }

    public String recreationAccessToken(String username, Object roles){
        Claims claims = Jwts.claims().setSubject(username);  // JWT payload 에 저장되는 정보단위
        claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
        System.out.println(accessToken);
        return accessToken;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

}