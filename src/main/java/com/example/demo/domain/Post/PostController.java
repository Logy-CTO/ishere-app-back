package com.example.demo.domain.Post;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;


    @GetMapping("/main/{page}")
    public List<Post> getMainPosts(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 10); // 10개의 게시물을 한 페이지에 표시
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.getContent();
    }

    @GetMapping("/list")
    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    @GetMapping("/count")
    public long getPostCount() {
        return postRepository.count();
    }

    @PostMapping("/upload")
    public ResponseEntity writePost(@RequestBody PostDTO postDTO) {
        // SecurityContext에서 Authentication 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }

        // 인증된 사용자의 ID를 PostDTO에 설정
        postDTO.setUserId((Long) authentication.getPrincipal());
        //여기 수정해야함 TokenProvider보면 username(String)으로 받고있어서

        // 게시글 작성
        return ResponseEntity.ok(postService.writePost(postDTO));
    }


    //@GetMapping("/mypage")// 내가 쓴글 확인(마이페이지)
    //public List<PostDTO> getMyPage(HttpSession session) {
     //   long userId = (long) session.getAttribute("userId");
     //    List<PostDTO> posts = postService.getPostsByUserId(userId);
     //   return posts;
    //}//List<PostDTO> 객체를 JSON 형태로 변환하여 응답
}
