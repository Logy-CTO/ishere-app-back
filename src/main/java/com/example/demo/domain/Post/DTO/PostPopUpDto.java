package com.example.demo.domain.Post.DTO;


import com.example.demo.domain.Post.entity.Post;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPopUpDto {
    private int postId;
    private String categoryType;
    private String postTitle;
    private byte immediateCase;
    private int reward;
    private String userName;

}
