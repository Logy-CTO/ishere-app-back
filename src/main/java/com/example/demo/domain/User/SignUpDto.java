package com.example.demo.domain.User;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SignUpDto{
    private String userName;
    private String phoneNumber;
    private Long id;
    private String accountNumber;
    private String areaName;
    private String bankName;
    private String interestPost;
    private String realName;
}
