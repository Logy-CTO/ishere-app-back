package com.example.demo.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    // 아래 부터는 회원가입 시 기본값 설정
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "AREA_NAME")
    @ColumnDefault("서울 종로구") // 데이터베이스 생성시 디폴트값
    private String areaName;

    @Column(name = "BANK_NAME")
    private String bankName;

    //후에 postEntity 생성 후 조인 수정 필요
    @Column(name = "INTEREST_POST")
    @ColumnDefault("0L") // 데이터베이스 생성시 디폴트 값
    private Long interestPost;

    @Column(name = "REAL_NAME")
    private String realName;

    @Builder
    public User(Long id,
                String accountnumber,
                String areaname,
                String bankname,
                Long interestpost,
                String phonenumber,
                String realname,
                String username)
    {
        this.id = id;
        this.accountNumber = accountnumber;
        this.bankName = bankname;
        this.interestPost = interestpost;
        this.phoneNumber = phonenumber;
        this.realName = realname;
        this.userName = username;
        this.areaName = areaname;
    }
    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }//login
}