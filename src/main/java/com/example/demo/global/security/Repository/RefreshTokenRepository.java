package com.example.demo.global.security.Repository;

import com.example.demo.global.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    boolean existsByKeyPhonenumber(String Phonenumber);
    void deleteByKeyPhonenumber(String Phonenumber);
}