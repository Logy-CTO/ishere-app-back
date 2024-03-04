package com.example.demo.domain.Post;

import com.example.demo.domain.File.ImageUploadDTO;
import com.example.demo.domain.User.*;
import com.example.demo.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final PostRepository postRepository;
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

        // 사용자의 관심 post ID 리스트를 ","스플릿해서(서비스) 가져오기
        List<String> interestPosts = postService.getUserInterestPosts(phoneNumber);

        // post ID 리스트에 해당하는 post들을 한 번의 쿼리로 가져오기
        List<Integer> postIds = interestPosts.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<PostDTO> posts = postService.getPost(postIds);

        return ResponseEntity.ok(posts);
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

    /*
    ----------------------PostMapping------------------------
    */

    //글쓰기
    @PostMapping("/upload")
    public ResponseEntity writePost(@RequestBody PostDTO postDTO,
                                    @RequestBody ImageUploadDTO imageUploadDTO) {
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
        return ResponseEntity.ok(postService.writePost(postDTO, imageUploadDTO));
    }
    //게시글 수정

    /*
    ----------------------PutMapping------------------------
    */

    @PutMapping("/update")
    public ResponseEntity updatePost(@RequestBody PostDTO postDTO) {
        // SecurityContext에서 Authentication 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String phoneNumber = customUserDetails.getUsername();
        String userName = userService.findUserNameByPhoneNumber(phoneNumber);
        String areaName = userService.findAreaNameByPhoneNumber(phoneNumber);
        int postId = postDTO.getPostId();

        try {
            Post post = postService.findById(postId);
            if (!post.getUserName().equals(userName)) {
                throw new Exception("작성자만 게시글을 수정할 수 있습니다.");
            }

            // 게시글 정보 업데이트
            postService.updatePost(postId, postDTO.getPostTitle(), postDTO.getDescription(), postDTO.getReward(),
                    postDTO.getXLoc(), postDTO.getYLoc(), areaName, postDTO.getImmediateCase());

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