package com.example.demo.domain.Post;

import com.example.demo.domain.File.Images;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @OrderBy("id asc")
    private List<Images> postImages;

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
                String userName,
                List<Images> postImages
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
        this.postImages = postImages;
    }
    public void updatePost(String postTitle, String description,
                           int reward, double xLoc, double yLoc,
                           String areaName, byte immediateCase) {
        this.postTitle = postTitle;
        this.description = description;
        this.reward = reward;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.areaName = areaName;
        this.immediateCase = immediateCase;
    }

}