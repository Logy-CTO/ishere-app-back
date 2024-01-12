package com.example.demo.User;

import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;

@Getter
@Setter

@NoArgsConstructor
public class SignUpDto extends User {
    private String userName;
    private String phoneNumber;

    public SignUpDto(String userName,
                     String phoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

}