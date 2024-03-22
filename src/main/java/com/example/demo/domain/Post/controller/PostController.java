package com.example.demo.domain.Post.controller;


import com.example.demo.domain.Post.DTO.PostDTO;
import com.example.demo.domain.Post.DTO.PostPopUpDto;
import com.example.demo.domain.Post.DTO.PostResponseDTO;
import com.example.demo.domain.Post.DTO.PostUpdateDTO;

import com.example.demo.domain.Post.entity.Notice;
import com.example.demo.domain.Post.entity.Post;
import com.example.demo.domain.Post.repository.NoticeRepository;
import com.example.demo.domain.Post.repository.PostRepository;
import com.example.demo.domain.Post.service.PostService;

import com.example.demo.domain.User.*;
import com.example.demo.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final PostRepository postRepository;
    private final NoticeRepository noticeRepository;
    private final PostService postService;
    private final UserService userService;

    /*
    ----------------------GetMapping------------------------
    */

    //사용자에게 보여지는 최신 게시글 (postid로 내림차순)
    @GetMapping("/main/{page}")
    public List<PostResponseDTO> getMainPosts(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 30); // 30개의 게시물을 한 페이지에 표시
        return postService.allPage(pageable).getContent();
    }
    //찾았어요 최근 게시물 페이징 (postid로 내림차순)
    @GetMapping("/finder/{page}")
    public List<PostResponseDTO> getfinderPosts(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 30); // 30개의 게시물을 한 페이지에 표시
        return postService.finderCategoryPage(pageable).getContent();
    }
    //찾고있어요 최근 게시물 페이징 (postid로 내림차순)
    @GetMapping("/receive/{page}")
    public List<PostResponseDTO> getreceivePosts(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 30); // 30개의 게시물을 한 페이지에 표시
        return postService.receiveCategoryPage(pageable).getContent();
    }

    //사용자가 쓴 게시글 조회
    @GetMapping("/mypage")
    public ResponseEntity<List<Post>> getPostsByUserName(PostDTO postDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }

        // 인증된 사용자의 정보를 CustomUserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String phoneNumber = customUserDetails.getUsername();
        String userName = userService.findUserNameByPhoneNumber(phoneNumber);

        return ResponseEntity.ok(postService.getPostsByUserName(userName));
    }

    //사용자의 관심목록 게시글 확인
    @GetMapping("interestPost")
    public ResponseEntity<List<Post>> getPostsByUserInterestPost(PostDTO postDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }

        // 인증된 사용자의 정보를 CustomUserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String phoneNumber = customUserDetails.getUsername();
        String userName = userService.findUserNameByPhoneNumber(phoneNumber);

        return  ResponseEntity.ok(postService.getPostsByUserInterestPost(userName));
    }
    //게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable int postId) throws Exception {
        return ResponseEntity.ok(postService.findById(postId));
    }



    //게시글 관심목록추가(좋아요 누르기)


    @GetMapping("/notice")
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    //게시글 정렬
    @GetMapping("/list")
    public List<Post> getPostList() {
        return postRepository.findAll();
    }
    //게시글 세기
    @GetMapping("/count")
    public long getPostCount() {
        return postRepository.count();
    }
    //한눈에보기 마커 클릭 시 팝업창
    @GetMapping("/popUp")
    public ResponseEntity<PostPopUpDto> getPostPopUp(@RequestBody PostPopUpDto postPopUpDto) {
        return ResponseEntity.ok(postService.getPostPopUp(postPopUpDto.getPostId()));
    }

    /*
    ----------------------PostMapping------------------------
    */

    @PostMapping(value = "/upload",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity writePost(@RequestPart PostDTO postDTO,
                                    @RequestPart List<MultipartFile> files) {
        // SecurityContext에서 Authentication 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }


        // 인증된 사용자의 정보를 CustomUserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // CustomUserDetails에서 phone_number를 얻어 UserService를 통해 user_name을 찾음
        String phoneNumber = customUserDetails.getUsername();
        String userName = userService.findUserNameByPhoneNumber(phoneNumber);
        String areaName = userService.findAreaNameByPhoneNumber(phoneNumber);

        // 찾은 user_name, area_name을 PostDTO에 설정
        postDTO.setUserName(userName);
        postDTO.setAreaName(areaName);

        // 게시글 작성
        return ResponseEntity.ok(postService.writePost(postDTO, files));
    }
    //게시글 수정

    //게시글 관심목록추가(좋아요 누르기)
    @PostMapping("/addInterestPost")
    public ResponseEntity addInterestPost(@RequestBody InterestPostDto interestPostDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }

        // 인증된 사용자의 정보를 CustomUserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //customUserDetails.getUsername() -> PhoneNumber임
        postService.addInterestPost(customUserDetails.getUsername(), interestPostDto);
        return ResponseEntity.ok().build();
    }

    /*
    ----------------------PutMapping------------------------
    */
    //게시글 수정
    @PutMapping("/update")
    public ResponseEntity updatePost(@RequestBody PostDTO postDTO, PostUpdateDTO postUpdateDTO) {
        // SecurityContext에서 Authentication 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String phoneNumber = customUserDetails.getUsername();
        String userName = userService.findUserNameByPhoneNumber(phoneNumber);
        int postId = postDTO.getPostId();

        try {
            Post post = postService.findById(postId);
            if (!post.getUserName().equals(userName)) {
                throw new Exception("작성자만 게시글을 수정할 수 있습니다.");
            }
            // 게시글 정보 업데이트
            postService.updatePost(postId, postUpdateDTO);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("게시글 수정에 실패하였습니다. : " + e.getMessage());
        }
    }
    /*
    ----------------------DeleteMapping------------------------
    */
    @DeleteMapping("/delete")
    public ResponseEntity deletePost(@RequestBody PostDTO postDTO) throws Exception {
        // SecurityContext에서 Authentication 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String phoneNumber = customUserDetails.getUsername();
        String userName = userService.findUserNameByPhoneNumber(phoneNumber);
        int postId = postDTO.getPostId();

        try {
            Post post = postService.findById(postId);
            if (!post.getUserName().equals(userName)) {
                throw new Exception("작성자만 게시글을 삭제할 수 있습니다.");
            }

            postService.deletePost(postId);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("게시글 삭제에 실패하였습니다. : " + e.getMessage());
        }
    }
    }