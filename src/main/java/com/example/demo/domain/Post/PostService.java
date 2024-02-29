package com.example.demo.domain.Post;

import com.example.demo.domain.Post.LocationFind.LocationFind;
import com.example.demo.domain.Post.LocationFind.LocationFindRepository;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final LocationFindRepository locationFindRepository;


    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    //postid로 여러 게시글 불러오기 메서드
    public List<PostDTO> getPost(List<Integer> postIds) {
        List<Post> posts = postRepository.findByPostIdIn(postIds);
        return posts.stream()
                .map(PostDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //","스플릿해서 user의 관심게시글 가져오기
    public List<String> getUserInterestPosts(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return Arrays.asList(user.getInterestPost().split(","));
    }

    //마이페이지 본인이 작성한 게시글 조회
    public List<PostDTO> getPostsByUserName(String userName) {
        List<Post> posts = postRepository.findByUserName(userName);
        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostDTO postDTO = postMapper.toDto(post);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }
    //글쓰기
    public Post writePost(PostDTO postDto) {
        Post post = postDto.toWrite();
        post = postRepository.save(post);

        //한눈에 보기 테이블에 post_id와 좌표값 저장
        LocationFind locationFind = new LocationFind();
        locationFind.setPostId(post.getPostId());
        locationFind.setXLoc(post.getXLoc());
        locationFind.setYLoc(post.getYLoc());
        locationFind.setImmediateCase(post.getImmediateCase());
        locationFindRepository.save(locationFind);

        return post;
    }
}