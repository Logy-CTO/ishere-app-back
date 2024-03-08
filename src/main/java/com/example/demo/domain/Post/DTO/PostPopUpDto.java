package com.example.demo.domain.Post.DTO;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PostPopUpDto {
    private int postId;
    private String categoryType;
    private String postTitle;
    private byte immediateCase;
    private int reward;
    private String userName;
    public PostPopUpDto(int postId, String categoryType, String postTitle, byte immediateCase, int reward, String userName) {
        this.postId = postId;
        this.categoryType = categoryType;
        this.postTitle = postTitle;
        this.immediateCase = immediateCase;
        this.reward = reward;
        this.userName = userName;
    }
}
