package com.example.demo.domain.Post.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@IdClass(InterestPostId.class)
@Table(name = "interest_post")
@AllArgsConstructor
@NoArgsConstructor
public class InterestPost {

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Id
    @Column(name = "post_id", nullable = false)
    private int postId;

}