package com.example.demo.global.dto;

import lombok.Getter;
import lombok.Setter;

/***
 * JoinDTO 사용 안합니다. UserServiceImpl 참조 하세요!
 */


@Setter
@Getter
public class LoginDTO {

    private String username;
    private String phoneNumber;
    private String password;
}