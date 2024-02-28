package com.example.demo.domain.Post;

import java.time.LocalDateTime;

import lombok.*;

import java.time.LocalDateTime;


@Data
//@Data 어노테이션은 @Getter, @Setter, @ToString,
//@EqualsAndHashCode, @RequiredArgsConstructor,@NoArgsConstructor 등을 포함
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

    //글쓰기
    public Post toWrite() {
        return Post.builder()
                .postId(this.postId)
                .categoryType(this.categoryType)
                .postTitle(this.postTitle)
                .immediateCase(this.immediateCase)
                .reward(this.reward)
                .description(this.description)
                .createdAt(LocalDateTime.now()) // 현재 시간으로 설정
                .transactionStatus((byte) 0) // TransactionStatus를 항상 0으로 초기화
                .areaName(this.areaName)
                .xLoc(this.xLoc)
                .yLoc(this.yLoc)
                .userName(this.userName)
                .build();
    }

}