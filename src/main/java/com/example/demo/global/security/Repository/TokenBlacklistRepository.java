package com.example.demo.global.security.Repository;

import com.example.demo.global.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    // 추가적인 메서드가 필요하다면 여기에 선언할 수 있음
}