package com.example.demo.global.security.service;

import com.example.demo.global.entity.RefreshToken;
import com.example.demo.global.security.Repository.RefreshTokenRepository;
import com.example.demo.global.security.token.JwtTokenProvider;
import com.example.demo.global.entity.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// JWTUserService 클래스는 사용자의 인증 및 토큰 생성을 담당하는 서비스 클래스이다.
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void login(Token tokenDto){

        RefreshToken refreshToken = RefreshToken.builder().keyPhonenumber(tokenDto.getKey()).refreshToken(tokenDto.getRefreshToken()).build();
        String loginUserPhonenumber = refreshToken.getKeyPhonenumber();
        if(refreshTokenRepository.existsByKeyPhonenumber(loginUserPhonenumber)){
            log.info("기존의 존재하는 refresh 토큰 삭제");
            refreshTokenRepository.deleteByKeyPhonenumber(loginUserPhonenumber);
        }
        refreshTokenRepository.save(refreshToken);

    }

    public Optional<RefreshToken> getRefreshToken(String refreshToken){

        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public Map<String, String> validateRefreshToken(String refreshToken){
        RefreshToken refreshToken1 = getRefreshToken(refreshToken).get();
        System.out.println(refreshToken1);
        String createdAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken1);

        return createRefreshJson(createdAccessToken);
    }

    public Map<String, String> createRefreshJson(String createdAccessToken){
        // 새로 만들어진 AccessToken이 없으면
        Map<String, String> map = new HashMap<>();

        System.out.println("새로만들어진 Access 토큰 : "+createdAccessToken);
        if(createdAccessToken == null){

            map.put("errortype", "Forbidden");
            map.put("status", "402");
            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");

            return map;
        }
        //기존에 존재하는 accessToken 제거

        // 새로 만들어진 AccessToken이 있으면
        map.put("status", "200");
        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
        map.put("accessToken", createdAccessToken);

        return map;
        
    }
    
}