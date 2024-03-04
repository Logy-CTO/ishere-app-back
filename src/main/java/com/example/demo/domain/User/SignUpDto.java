package com.example.demo.domain.User;

import lombok.*;


@Data
//@Data 어노테이션은 @Getter, @Setter, @ToString,
//@EqualsAndHashCode, @RequiredArgsConstructor,@NoArgsConstructor 등을 포함
public class SignUpDto{
    private String userName;
    private String phoneNumber;
    private Long id;
    private String accountNumber;
    private String areaName;
    private String bankName;
    private String realName;
    private String pinNumber;

}