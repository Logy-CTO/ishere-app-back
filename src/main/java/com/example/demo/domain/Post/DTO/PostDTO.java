package com.example.demo.domain.Post.DTO;

import com.example.demo.domain.Image.PostImage;
import com.example.demo.domain.Post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private List<String> imageUrls;
//   private List<PostImage> postImages;

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

    public PostDTO  (Post post){
        this.imageUrls = post.getPostImages().stream().map(PostImage::getImg_url).collect(Collectors.toList());
    }

    public static PostDTO fromEntity(Post post, List<String> postImagesURL) {
        return PostDTO.builder()
                .postId(post.getPostId())
                .categoryType(post.getCategoryType())
                .postTitle(post.getPostTitle())
                .immediateCase(post.getImmediateCase())
                .reward(post.getReward())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .transactionStatus(post.getTransactionStatus())
                .areaName(post.getAreaName())
                .xLoc(post.getXLoc())
                .yLoc(post.getYLoc())
                .userName(post.getUserName())
                .imageUrls(postImagesURL)
                .build();
    }
}