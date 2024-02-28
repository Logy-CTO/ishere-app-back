package com.example.demo.domain.User;


import com.example.demo.domain.Post.PostDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    @Transactional
    User registerUser(@RequestBody SignUpDto signUpDto);

    LoginDto loginUser(LoginDto loginDto, HttpSession session);
    List<PostDTO> getUserPosts(HttpSession session);
    String findUserNameByPhoneNumber(String phoneNumber);
    String findAreaNameByPhoneNumber(String phoneNumber);

    boolean checkPinNumber(String phoneNumber, String pinNumber);

    User updateProfile(SignUpDto signUpDto);
}

