package com.example.demo.User;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class SignUpDto{
    private String userName;
    private String phoneNumber;
}