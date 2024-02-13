package com.example.demo.domain.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public User signUpDtoToUser(SignUpDto signupDto) {
        return User.builder()
                .id(signupDto.getId() != null ? signupDto.getId() : 0L)
                .accountNumber(signupDto.getAccountNumber() != null ? signupDto.getAccountNumber() : "")
                .areaName(signupDto.getAreaName() != null ? signupDto.getAreaName() : "서울 종로구")
                .bankName(signupDto.getBankName() != null ? signupDto.getBankName() : "")
                .interestPost(signupDto.getInterestPost() != null ? signupDto.getInterestPost() : "")
                .phoneNumber(signupDto.getPhoneNumber())
                .realName(signupDto.getRealName() != null ? signupDto.getRealName() : "")
                .userName(signupDto.getUserName())
                .build();
    }
}