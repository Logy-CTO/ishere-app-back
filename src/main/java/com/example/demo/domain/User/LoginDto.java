<<<<<<< HEAD:src/main/java/com/example/demo/domain/User/LoginDto.java
package com.example.demo.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

    private String phoneNumber;

    public LoginDto(String phoneNumber){
        this.phoneNumber = phoneNumber;

    }

=======
package com.example.demo.User;
import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

    private String phoneNumber;

    public LoginDto(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

>>>>>>> b6b88a1f87b098b9cc0f0df6c91bb3b67b71b298:src/main/java/com/example/demo/User/LoginDto.java
}