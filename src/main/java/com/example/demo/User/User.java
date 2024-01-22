package com.example.demo.User;

import com.example.demo.Post.Post;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import javax.persistence.OneToMany;
@Entity
@Getter
@Setter
@Table(name = "USER")
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

    @Column(name = "INTEREST_POST")
    private String interestPost;

    @Column(name = "REAL_NAME")
    private String realName;

    @Builder
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
}
