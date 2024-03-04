package com.example.demo.domain.User;

import com.example.demo.domain.Post.Post;
import com.example.demo.domain.Post.PostDTO;
import com.example.demo.domain.Post.PostRepository;
import com.example.demo.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /*
    ----------------------GetMapping------------------------
    */




    /*
    ----------------------PostMapping------------------------
    */
    //회원가입
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody SignUpDto signUpDto) {
        User registeredUser = userService.registerUser(signUpDto);
        return ResponseEntity.ok(registeredUser);
    }
    //핀번호(결제)
    @PostMapping("/check-pin")
    public ResponseEntity<String> checkPinNumber(@RequestBody SignUpDto request) {
        boolean isValid = userService.checkPinNumber(request.getPhoneNumber(), request.getPinNumber());
        if (isValid) {
            return ResponseEntity.ok("Valid Pin Number");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Pin Number");
        }
    }

    //사용자 마이페이지에서 개인정보 수정(닉네임, 은행계좌 정보 -> 나중에 분리해야할듯)
    @PostMapping("/update")
    public ResponseEntity<User> updateProfile(@RequestBody SignUpDto signUpDto) {
        // SecurityContext에서 Authentication 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }

        // 인증된 사용자의 정보를 CustomUserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        //customUserDetails.getUsername() -> PhoneNumber임
        signUpDto.setPhoneNumber(customUserDetails.getUsername());

        // 사용자 프로필 업데이트
        User updatedUser = userService.updateProfile(signUpDto);
        return ResponseEntity.ok(updatedUser);
    }

    //게시글 관심목록추가(좋아요 누르기)
    @PostMapping("/addInterestPost")
    public ResponseEntity<Void> addInterestPost(@RequestBody InterestPostDto interestPostDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }

        // 인증된 사용자의 정보를 CustomUserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //customUserDetails.getUsername() -> PhoneNumber임
        userService.addInterestPost(customUserDetails.getUsername(), interestPostDto);
        return ResponseEntity.ok().build();
    }
/*
    ----------------------PutMapping------------------------
*/
    //게시판 변경
    @PutMapping("/updateArea")
    public ResponseEntity<Void> updateArea(@RequestBody UserAreaDto userAreaDto){
        // SecurityContext에서 Authentication 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }

        // 인증된 사용자의 정보를 CustomUserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //Username은 phoneNumber로 들어감
        userService.updateArea(customUserDetails.getUsername(),userAreaDto);
        return ResponseEntity.ok().build();
    }
}