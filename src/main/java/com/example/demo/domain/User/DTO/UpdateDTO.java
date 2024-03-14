package com.example.demo.domain.User.DTO;

import lombok.*;
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateDTO {
    private String userName;
    private String accountNumber;
    private String bankName;
    private String realName;
    private String pinNumber;
}
