package com.example.demo.User;

import com.example.demo.Post.Post;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.persistence.OneToMany;
@Entity
@Getter
@Table(name = "USER")
@NoArgsConstructor
public class User {
    @Id
    //gerneratedVALUE SQL 시퀀스 사용 후 수정필요
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "phone_number")
    private String phoneNumber;
    // 아래 부터는 회원가입 시 기본값 설정
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "AREA_NAME") // 데이터베이스 생성시 디폴트값
    private String areaName;

    @Column(name = "BANK_NAME")
    private String bankName;

    //후에 postEntity 생성 후 조인 수정 필요

    @Column(name = "INTEREST_POST") // 데이터베이스 생성시 디폴트 값
    private String interestPost;

    @Column(name = "REAL_NAME")
    private String realName;


    @Builder(toBuilder = true)
    public User(Long id,
                String accountNumber,
                String areaName,
                String bankName,
                String interestPost,
                String phoneNumber,
                String realName,
                String userName)
    {
        this.id = id;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.interestPost = interestPost;
        this.phoneNumber = phoneNumber;
        this.realName = realName;
        this.userName = userName;
        this.areaName = areaName;
    }
    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }//login
}