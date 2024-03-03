package com.example.demo.domain.User;


import com.example.demo.domain.Post.PostDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    //회원가입
    @Transactional
    User registerUser(@RequestBody SignUpDto signUpDto);
    //글쓰기 닉네임 지정
    String findUserNameByPhoneNumber(String phoneNumber);
    //글쓰기 지역 지정
    String findAreaNameByPhoneNumber(String phoneNumber);
    //핀번호(결제) 확인
    boolean checkPinNumber(String phoneNumber, String pinNumber);
    //마이페이지 수정
    User updateProfile(SignUpDto signUpDto);
    void addInterestPost(String phoneNumber, InterestPostDto interestPostDto);

}

