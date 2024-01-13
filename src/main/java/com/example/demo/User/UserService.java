package com.example.demo.User;


import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
public interface UserService {
    @Transactional
    User registerUser(SignUpDto signUpDto);
    LoginDto loginUser(LoginDto loginDto, HttpSession session);
}