package com.example.demo.domain.Post.DTO;

import com.example.demo.domain.Post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
//페이징 처리시에 기본정보를 받을 DTO
public class PostResponseDTO {
    private int postId;
    private String categoryType;
    private String username;
    private String postTitle;
    private byte immediateCase;
    private int reward;
    private LocalDateTime createdAt;
    private byte transactionStatus;


    public static PostResponseDTO from(Post post) {
        return PostResponseDTO.builder()
                .postId(post.getPostId())
                .categoryType(post.getCategoryType())
                .username(post.getUserName())
                .immediateCase(post.getImmediateCase())
                .reward(post.getReward())
                .createdAt(post.getCreatedAt())
                .transactionStatus(post.getTransactionStatus())
                .build();
    }
}
