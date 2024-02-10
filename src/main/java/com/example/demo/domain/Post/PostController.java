package com.example.demo.domain.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import javax.servlet.http.HttpSession;
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

    //@GetMapping("/mypage")// 내가 쓴글 확인(마이페이지)
    //public List<PostDTO> getMyPage(HttpSession session) {
     //   long userId = (long) session.getAttribute("userId");
     //    List<PostDTO> posts = postService.getPostsByUserId(userId);
     //   return posts;
    //}//List<PostDTO> 객체를 JSON 형태로 변환하여 응답
}
