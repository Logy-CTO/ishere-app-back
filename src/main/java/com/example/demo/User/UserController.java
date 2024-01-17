package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpSession;
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")//플러터로 변경시 @requestbody로 변경
    public SignUpDto registerUser(@ModelAttribute SignUpDto signUpDto) {
        User registeredUser = userService.registerUser(signUpDto);
        return new SignUpDto(
                registeredUser.getUserName(),
                registeredUser.getPhoneNumber(),
                registeredUser.getId(),
                registeredUser.getAccountNumber(),
                registeredUser.getAreaName(),
                registeredUser.getBankName(),
                registeredUser.getInterestPost(),
                registeredUser.getRealName()
        );
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
    @PostMapping("/logout")
    public void logoutUser(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

}