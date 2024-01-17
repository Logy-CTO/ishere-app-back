package com.example.demo.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }
    public List<PostDTO> getPostsByUserName(long userName) {
        List<Post> posts = postRepository.findByUserName(userName);
        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostDTO postDTO = PostDTO.fromEntity(post);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }
}//PostDTO::fromEntity -> Post를 PostDTO로 변환
    // collect(Collectors.toList())->collector로 Stream의 결과를 새로운 List로 변환

