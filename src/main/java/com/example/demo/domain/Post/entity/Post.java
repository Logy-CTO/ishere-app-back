package com.example.demo.domain.Post.entity;

import com.example.demo.domain.Image.PostImage;
import com.example.demo.domain.Post.DTO.PostUpdateDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private int postId;

    @Column(name = "CATEGORY_TYPE")
    private String categoryType;

    @Column(name = "POST_TITLE")
    private String postTitle;

    @Column(name = "IMMEDIATE_CASE")
    private byte immediateCase;

    @Column(name = "REWARD")
    private int reward;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "TRANSACTION_STATUS")
    private byte transactionStatus;

    @Column(name = "AREA_NAME")
    private String areaName;
    @Column(name = "X_LOC")
    private Double xLoc;
    @Column(name = "Y_LOC")
    private Double yLoc;
    @Column(name = "USER_NAME")
    private String userName;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("image_id asc")
    private List<PostImage> postImages = new ArrayList<>();
    @Builder
    public Post(int postId,
                String categoryType,
                String postTitle,
                byte immediateCase,
                int reward,
                String description,
                LocalDateTime createdAt,
                byte transactionStatus,
                String areaName,
                double xLoc,
                double yLoc,
                String userName
    )
    {
        this.postId = postId;
        this.categoryType = categoryType;
        this.postTitle = postTitle;
        this.immediateCase = immediateCase;
        this.reward = reward;
        this.description = description;
        this.createdAt = createdAt;
        this.transactionStatus = transactionStatus;
        this.areaName = areaName;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.userName = userName;
    }

    public void addPostImage(PostImage image) {
        postImages.add(image);
        image.setPost(this);
    }
    public void updatePostFromDto(PostUpdateDTO postUpdateDTO) {
        this.postTitle = postUpdateDTO.getPostTitle(); // DTO에서 값을 가져와 현재 객체의 필드를 업데이트
        this.description = postUpdateDTO.getDescription();
        this.reward = postUpdateDTO.getReward();
        this.xLoc = postUpdateDTO.getXLoc();
        this.yLoc = postUpdateDTO.getYLoc();
        this.areaName = postUpdateDTO.getAreaName();
        this.immediateCase = postUpdateDTO.getImmediateCase(); // boolean 타입의 경우 getter 메서드 명명 규칙이 다를 수 있음
    }


}