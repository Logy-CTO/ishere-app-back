package com.example.demo.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "AREA_NAME")
    private String areaName;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "REAL_NAME")
    private String realName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "pin_number")
    private String pinNumber;

    @Builder
    public User(Long id,
                String accountNumber,
                String areaName,
                String bankName,
                String phoneNumber,
                String realName,
                String userName,
                String password,
                String role,
                String pinNumber)
    {
        this.id = id;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.phoneNumber = phoneNumber;
        this.realName = realName;
        this.userName = userName;
        this.areaName = areaName;
        this.password = password;
        this.role = role;
        this.pinNumber = pinNumber;
    }

    // ("/update") -> setter를 쓰지 않기 위한 조치
    public void updateUserName(String userName) {
        if (userName != null) {
            this.userName = userName;
        }
    }
    public void updateRealName(String realName) {
        if (realName != null) {
            this.realName = realName;
        }
    }
    public void updateBankName(String bankName) {
        if (bankName != null) {
            this.bankName = bankName;
        }
    }

    public void updateAccountNumber(String accountNumber) {
        if (accountNumber != null) {
            this.accountNumber = accountNumber;
        }
    }
    //회원가입 Dto
    public User(String userName, String phoneNumber, String password, String role, String realName, String bankName, String accountNumber, String areaName, String interestPost) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.realName = realName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.areaName = areaName;
    }

    public User(String phoneNumber, String areaName) {
        this.phoneNumber = phoneNumber;
        this.areaName = areaName;
    }

    public void updateArea(String areaName) {
        this.areaName = areaName;
    }
}
