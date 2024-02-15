package com.example.demo.domain.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public List<PostDTO> getPostsByUserId(long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostDTO postDTO = postMapper.toDto(post);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }
    public Post writePost(PostDTO postDto) {
        Post post = Post.builder()
                .postId(postDto.getPostId())
                .userId(postDto.getUserId())
                .categoryType(postDto.getCategoryType())
                .postTitle(postDto.getPostTitle())
                .immediateCase(postDto.getImmediateCase())
                .reward(postDto.getReward())
                .description(postDto.getDescription())
                .createdAt(postDto.getCreatedAt())
                .transactionStatus((byte) 0) // TransactionStatus를 항상 0으로 초기화
                .areaName(postDto.getAreaName())
                .xLoc(postDto.getXLoc())
                .yLoc(postDto.getYLoc())
                .build();
        return postRepository.save(post);
    }
}//PostDTO::fro
