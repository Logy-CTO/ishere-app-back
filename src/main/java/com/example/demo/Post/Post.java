package com.example.demo.Post;
import com.example.demo.User.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.ManyToOne;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Post")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private int postId;

    @Column(name = "USER_ID")
    private long userId;

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

    @Column(name = "LOCATION")
    private double location;

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

    @Builder
    public Post(int postId,
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
                double yLoc)
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

    public Post() {

    }
}
