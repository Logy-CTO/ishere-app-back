package com.example.demo.domain.Post.DTO;

import com.example.demo.domain.Image.PostImage;
import com.example.demo.domain.Post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
//페이징 처리시에 기본정보를 받을 DTO
public class PostResponseDTO {
    private int postId;
    private String categoryType;
    private String username;
    private String postTitle;
    private String imageUrls;
    private byte immediateCase;
    private int reward;
    private LocalDateTime createdAt;
    private byte transactionStatus;


    public static PostResponseDTO from(Post post) {
        String firstImageUrl = post.getPostImages().stream()
                .map(PostImage::getImg_url)
                .findFirst()
                .orElse(null);

        return PostResponseDTO.builder()
                .postId(post.getPostId())
                .categoryType(post.getCategoryType())
                .username(post.getUserName())
                .postTitle(post.getPostTitle())
                .imageUrls(firstImageUrl)
                .immediateCase(post.getImmediateCase())
                .reward(post.getReward())
                .createdAt(post.getCreatedAt())
                .transactionStatus(post.getTransactionStatus())
                .build();
    }
}
