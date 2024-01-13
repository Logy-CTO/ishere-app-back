package com.example.demo.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User signupDtoToUser(SignUpDto signupDto);
    SignUpDto userToSignUpDto(User user);
    User loginDtoToUser(LoginDto loginDto);

}
