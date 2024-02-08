package com.example.demo.User;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpSession;
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor // 생성자 주입 *강조*
public class UserController {
    //생성자 주입 *강조*
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<SignUpDto> registerUser(@ModelAttribute SignUpDto signUpDto) {
        try {
            userService.registerUser(signUpDto);
            return new ResponseEntity<>(signUpDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginDto> loginUser(@ModelAttribute LoginDto loginDto, HttpSession session) {
        try {
            LoginDto loginUserDto = userService.loginUser(loginDto, session);
            return new ResponseEntity<>(loginUserDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}