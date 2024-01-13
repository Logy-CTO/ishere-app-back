package com.example.demo.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

    private String phoneNumber;
    @Builder
    public LoginDto(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}