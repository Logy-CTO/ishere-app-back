package com.example.demo.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "USER")
@NoArgsConstructor
public class User {
    @Id
    //gerneratedVALUE SQL 시퀀스 사용 후 수정필요
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    // 아래 부터는 회원가입 시 기본값 설정
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "AREA_NAME")
    private String areaName;

    @Column(name = "BANK_NAME")
    private String bankName;

    //후에 postEntity 생성 후 조인 수정 필요
    @Column(name = "INTEREST_POST")
    private Long interestPost;

    @Column(name = "REAL_NAME")
    private String realName;
    public User(String userName, String phoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.accountNumber = "";
        this.areaName = "서울 종로구";
        this.bankName = "";
        this.interestPost = 0L;
        this.realName = "";
    }//signUp
    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }//login
}