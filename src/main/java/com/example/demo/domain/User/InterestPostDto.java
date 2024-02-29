package com.example.demo.domain.User;

import lombok.Data;

@Data
public class InterestPostDto {

    private Long postId;
    private String interestPost;

    // 관심목록 추가 메서드 "{PostId},{PostId}," <- 이렇게 저장
    public void addInterestPost() {
        if (interestPost == null) {
            interestPost = "";
        }
        interestPost += postId + ",";
    }
}