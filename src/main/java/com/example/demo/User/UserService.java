package com.example.demo.User;


import com.example.demo.Post.PostDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    @Transactional
    User registerUser(SignUpDto signUpDto);
    LoginDto loginUser(LoginDto loginDto, HttpSession session);
    List<PostDTO> getUserPosts(HttpSession session);
}

