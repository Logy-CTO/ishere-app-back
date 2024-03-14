package com.example.demo.domain.User.DTO;
import lombok.*;

@Data
//@Data 어노테이션은 @Getter, @Setter, @ToString,
//@EqualsAndHashCode, @RequiredArgsConstructor,@NoArgsConstructor 등을 포함
public class LoginDto {

    private String phoneNumber;
    private String password;

    public LoginDto(String phoneNumber){
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}