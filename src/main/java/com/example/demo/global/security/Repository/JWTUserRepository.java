package com.example.demo.global.security.Repository;

import com.example.demo.global.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JWTUserRepository extends JpaRepository<UserEntity, Integer> {
    //username을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    UserEntity findByUsername(String username);
}