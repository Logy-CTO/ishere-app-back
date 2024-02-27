package com.example.demo.global.security.service;

import com.example.demo.global.entity.TokenBlacklist;
import com.example.demo.global.security.Repository.TokenBlacklistRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenBlacklistService {

    private final TokenBlacklistRepository tokenBlacklistRepository;

    public TokenBlacklistService(TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    public void addToBlacklist(String token, LocalDateTime expirationTime) {
        TokenBlacklist tokenBlacklist = new TokenBlacklist();
        tokenBlacklist.setToken(token);
        tokenBlacklist.setExpirationTime(expirationTime); // 만료 시간 설정
        tokenBlacklistRepository.save(tokenBlacklist);
    }
}
