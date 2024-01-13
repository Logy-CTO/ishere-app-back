package com.example.demo.User;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SignUpDto{
    private String userName;
    private String phoneNumber;

}
