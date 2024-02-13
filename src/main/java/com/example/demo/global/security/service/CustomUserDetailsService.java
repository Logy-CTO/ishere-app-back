package com.example.demo.global.security.service;

import com.example.demo.global.security.principal.CustomUserDetails;
import com.example.demo.global.entity.UserEntity;
import com.example.demo.global.security.Repository.JWTUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// UserDetailsService를 구현한 클래스
// 사용자 정보를 데이터베이스에서 가져와 UserDetails 객체로 변환하는 역할을 수행한다.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JWTUserRepository userRepository;

    // UserRepository 주입 받음
    public CustomUserDetailsService(JWTUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 주어진 사용자 이름에 해당하는 사용자 정보를 데이터베이스에서 찾아 UserDetails 객체로 반환
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 이름으로 데이터베이스에서 사용자 정보 가져오기
        UserEntity userData = userRepository.findByUsername(username);
        // 사용자 정보가 없으면 예외 발생
        if (userData == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // UserDetails 객체로 변환하여 반환
        return new CustomUserDetails(userData);
    }
}
