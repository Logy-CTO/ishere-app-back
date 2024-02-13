package com.example.demo.global.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "phone_number")
    private String username;

    private String password;

    private String role; // 유저에 대한 권한을 줌.
}