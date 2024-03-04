package com.example.demo.domain.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public User signUpDtoToUser(SignUpDto signUpDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String role = "ROLE_ADMIN"; // 기본 권한 설정
        String encryptedPinNumber = passwordEncoder.encode(signUpDto.getPinNumber());
        String defaultPassword = passwordEncoder.encode("울트라킹왕짱코딩의신택수");
        return User.builder()
                .id(signUpDto.getId() != null ? signUpDto.getId() : 0L)
                .accountNumber(signUpDto.getAccountNumber() != null ? signUpDto.getAccountNumber() : "")
                .areaName(signUpDto.getAreaName() != null ? signUpDto.getAreaName() : "서울 종로구")
                .bankName(signUpDto.getBankName() != null ? signUpDto.getBankName() : "")
                .phoneNumber(signUpDto.getPhoneNumber())
                .realName(signUpDto.getRealName() != null ? signUpDto.getRealName() : "")
                .userName(signUpDto.getUserName())
                .password(defaultPassword)
                .role(role)
                .pinNumber(encryptedPinNumber)
                .build();
    }

}