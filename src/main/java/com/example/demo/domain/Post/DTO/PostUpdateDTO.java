package com.example.demo.domain.Post.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDTO {
    private String postTitle;
    private String description;
    private int reward;
    private double xLoc;
    private double yLoc;
    private String areaName;
    private byte immediateCase;
}
