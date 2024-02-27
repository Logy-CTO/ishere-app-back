package com.example.demo.domain.Post;

import java.time.LocalDateTime;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Data
public class PostDTO {

    private int postId;
    private String categoryType;
    private String postTitle;
    private byte immediateCase;
    private int reward;
    private String description;
    private LocalDateTime createdAt;
    private byte transactionStatus;
    private String areaName;
    private double xLoc;
    private double yLoc;
    private String userName;
    @Builder
    public PostDTO(
            int postId,
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
    public void setUserName(String userName) {
        this.userName = userName;
    }

}