package com.example.demo.domain.Post.controller;

<<<<<<< HEAD:src/main/java/com/example/demo/domain/Post/PostController.java
import com.example.demo.domain.Image.ImageUploadDTO;
import com.example.demo.domain.Post.DTO.PostDTO;
import com.example.demo.domain.Post.DTO.PostPopUpDto;
import com.example.demo.domain.Post.DTO.PostUpdateDTO;
=======
import com.example.demo.domain.File.ImageUploadDTO;
import com.example.demo.domain.Post.entity.Notice;
import com.example.demo.domain.Post.entity.Post;
import com.example.demo.domain.Post.dto.PostDTO;
import com.example.demo.domain.Post.repository.InterestPostRepository;
import com.example.demo.domain.Post.repository.NoticeRepository;
import com.example.demo.domain.Post.repository.PostRepository;
import com.example.demo.domain.Post.service.PostService;
>>>>>>> main:src/main/java/com/example/demo/domain/Post/controller/PostController.java
import com.example.demo.domain.User.*;
import com.example.demo.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.stream.Collectors;

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

    //사용자에게 보여지는 최신 게시글
    @GetMapping("/main/{page}")
    public List<Post> getMainPosts(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 10); // 10개의 게시물을 한 페이지에 표시
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.getContent();
    }
    //사용자가 쓴 게시글 조회
    @GetMapping("/mypage")
    public ResponseEntity<List<PostDTO>> getPostsByUserName(PostDTO postDTO){
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

    //사용자의 관심있는 게시글보기
    @GetMapping("/interestPost")
    public ResponseEntity<List<PostDTO>> getPostsByUserInterestPost(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }

        // 인증된 사용자의 정보를 CustomUserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String phoneNumber = customUserDetails.getUsername();

        List<Integer> interestPosts = postService.getUserInterestPosts(phoneNumber);

        // post ID 리스트에 해당하는 post들을 한 번의 쿼리로 가져오기
        List<PostDTO> posts = postService.getPost(interestPosts);

        return ResponseEntity.ok(posts);
    }

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