package com.example.demo.global.service;

import com.example.demo.global.dto.CustomUserDetails;
import com.example.demo.global.entity.UserEntity;
import com.example.demo.global.Repository.JWTUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JWTUserRepository userRepository;

    public CustomUserDetailsService(JWTUserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);
        if (userData == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(userData);
    }

}
