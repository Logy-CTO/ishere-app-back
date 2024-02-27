package com.example.demo.domain.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<PostDTO> getPostsByUserName(String userName) {
        List<Post> posts = postRepository.findByUserName(userName);
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
                .categoryType(postDto.getCategoryType())
                .postTitle(postDto.getPostTitle())
                .immediateCase(postDto.getImmediateCase())
                .reward(postDto.getReward())
                .description(postDto.getDescription())
                .createdAt(LocalDateTime.now()) // 현재 시간으로 설정
                .transactionStatus((byte) 0) // TransactionStatus를 항상 0으로 초기화
                .areaName(postDto.getAreaName())
                .xLoc(postDto.getXLoc())
                .yLoc(postDto.getYLoc())
                .userName(postDto.getUserName())
                .build();
        return postRepository.save(post);
    }
}//PostDTO::fro