package com.example.demo.domain.Post.service;

import com.example.demo.domain.File.FtpService;
import com.example.demo.domain.File.ImageRepository;
import com.example.demo.domain.File.ImageUploadDTO;
import com.example.demo.domain.File.Images;
import com.example.demo.domain.Post.LocationFind.LocationFind;
import com.example.demo.domain.Post.LocationFind.LocationFindRepository;
import com.example.demo.domain.Post.entity.InterestPost;
import com.example.demo.domain.Post.entity.Post;
import com.example.demo.domain.Post.dto.PostDTO;
import com.example.demo.domain.Post.dto.PostMapper;
import com.example.demo.domain.Post.repository.InterestPostRepository;
import com.example.demo.domain.Post.repository.PostRepository;
import com.example.demo.domain.User.InterestPostDto;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final LocationFindRepository locationFindRepository;
    private final ImageRepository imageRepository;
    private final FtpService ftpService;
    private final InterestPostRepository interestPostRepository;

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

    // 사용자가 관심있는 게시글 추가(좋아요 누르기)
    public void addInterestPost(String phoneNumber, InterestPostDto interestPostDto) {
        // 전화번호로 사용자를 확인하고, 사용자 이름을 가져오는 로직이 필요할 것입니다.
        // 여기서는 가정적으로 사용자 이름을 "phoneNumber"로 설정합니다.
        String userName = phoneNumber;

        // InterestPostDto에서 데이터를 가져와서 InterestPost 엔티티를 생성합니다.
        InterestPost interestPost = new InterestPost();
        interestPost.setUserName(userName);
        interestPost.setPostId(Math.toIntExact(interestPostDto.getPostId()));

        // 생성된 InterestPost를 저장합니다.
        interestPostRepository.save(interestPost);
    }

    /*
    //","스플릿해서 user의 관심게시글 가져오기
    public List<String> getUserInterestPosts(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return Arrays.asList(user.getInterestPost().split(","));
    }
     */

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
    @Transactional
    public Post writePost(PostDTO postDto, ImageUploadDTO imageUploadDTO) {
        Post post = postDto.toWrite();
        post = postRepository.save(post);

        //한눈에 보기 테이블에 post_id와 좌표값 저장
        LocationFind locationFind = new LocationFind();
        locationFind.setPostId(post.getPostId());
        locationFind.setXLoc(post.getXLoc());
        locationFind.setYLoc(post.getYLoc());
        locationFind.setImmediateCase(post.getImmediateCase());
        locationFindRepository.save(locationFind);


        //이미지 업로드
        if (imageUploadDTO.getFiles() != null && !imageUploadDTO.getFiles().isEmpty()) {
            for (MultipartFile file : imageUploadDTO.getFiles()) {
                UUID uuid = UUID.randomUUID();
                String imageFileName = uuid + "_" + file.getOriginalFilename();

                File destinationFile = new File("/home/www/html/images/" + imageFileName);
                ftpService.uploadFile(destinationFile.toString(), "/home/www/html/images/" + imageFileName);

                try {
                    file.transferTo(destinationFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Images image = Images.builder()
                        .img_url("http://113.131.111.147/images/" + imageFileName)
                        .image_name(imageFileName)
                        .post(post)
                        .build();

                imageRepository.save(image);
            }
        }


        return post;
    }

    public Post findById(int postId) throws Exception {
        return postRepository.findById(postId)
                .orElseThrow(() -> new Exception("해당 게시글을 찾을 수 없습니다. id=" + postId));
    }
    //게시글 수정
    @Transactional
    public void updatePost(int postId, String postTitle, String description, int reward, double xLoc, double yLoc, String areaName, byte immediateCase) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다. id=" + postId));

        post.updatePost(postTitle, description, reward, xLoc, yLoc, areaName, immediateCase);
    }
    //게시글 삭제
    @Transactional
    public void deletePost(int postId) throws Exception {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new Exception("해당 게시글을 찾을 수 없습니다. id=" + postId));

        postRepository.delete(post);
    }
}