package com.example.demo.Post;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private int postId;
    private long userId;
    private String categoryType;
    private String postTitle;
    private byte immediateCase;
    private int reward;
    private String description;
    private double location;
    private LocalDateTime createdAt;
    private byte transactionStatus;
    private String areaName;
    private double xLoc;
    private double yLoc;

    @Builder
    public PostDTO(
            int postId,
            long userId,
            String categoryType,
            String postTitle,
            byte immediateCase,
            int reward,
            String description,
            double location,
            LocalDateTime createdAt,
            byte transactionStatus,
            String areaName,
            double xLoc,
            double yLoc
    )
    {
        this.postId = postId;
        this.userId = userId;
        this.categoryType = categoryType;
        this.postTitle = postTitle;
        this.immediateCase = immediateCase;
        this.reward = reward;
        this.description = description;
        this.location = location;
        this.createdAt = createdAt;
        this.transactionStatus = transactionStatus;
        this.areaName = areaName;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

}