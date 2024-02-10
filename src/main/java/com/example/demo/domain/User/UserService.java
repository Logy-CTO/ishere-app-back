package com.example.demo.domain.User;


import com.example.demo.domain.Post.PostDTO;
import com.example.demo.global.dto.JoinDTO;
import com.example.demo.global.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    @Transactional
    User registerUser(SignUpDto signUpDto);
    LoginDto loginUser(LoginDto loginDto, HttpSession session);
    List<PostDTO> getUserPosts(HttpSession session);
    void updateProfile(SignUpDto signUpDto, HttpSession session);
}

