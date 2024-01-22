package com.example.demo.User;

import com.example.demo.Post.Post;
import com.example.demo.Post.PostDTO;
import com.example.demo.Post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")//플러터로 변경시 @requestbody로 변경
    public SignUpDto registerUser(@ModelAttribute SignUpDto signUpDto) {
        User registeredUser = userService.registerUser(signUpDto);
        return new SignUpDto(
                registeredUser.getUserName(),
                registeredUser.getPhoneNumber(),
                registeredUser.getId(),
                registeredUser.getAccountNumber(),
                registeredUser.getAreaName(),
                registeredUser.getBankName(),
                registeredUser.getInterestPost(),
                registeredUser.getRealName()
        );
    }
    @PostMapping("/login")
    public ResponseEntity<LoginDto> loginUser(@ModelAttribute LoginDto loginDto, HttpSession session) {
        try {
            LoginDto loginUserDto = userService.loginUser(loginDto, session);
            return new ResponseEntity<>(loginUserDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/logout")
    public void logoutUser(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    @GetMapping("/interest")
    public String getUserPosts(HttpSession session, Model model) {
        // 세션에서 현재 로그인한 사용자의 ID를 가져옵니다.
        Long loggedInUserId = (Long) session.getAttribute("userId");

        System.out.println(loggedInUserId);
        // 만약 로그인되지 않은 경우 로그인 페이지로 리다이렉트합니다.
        if (loggedInUserId == null) {
            // 로그인되지 않은 경우 로그인 페이지로 이동하거나 다른 처리를 수행할 수 있습니다.
            return "/smstest.html";
        }

        // 사용자 Repository를 사용하여 로그인한 사용자의 정보를 조회합니다.
        User user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 사용자의 관심 게시물 ID 목록을 가져와서 콤마로 구분된 문자열로 변환합니다.
        String interestPost = String.valueOf(user.getInterestPost());

        // 콤마로 구분된 문자열을 분리하여 게시물 ID 목록을 만듭니다.
        List<String> postIdStrings = Arrays.asList(interestPost.split(","));

        // 게시물 ID를 Long으로 변환하여 목록을 만듭니다.
        List<Integer> postIds = new ArrayList<>();
        for (String postIdString : postIdStrings) {
            postIds.add(Integer.parseInt(postIdString.trim()));
        }

        // 게시물 ID 목록을 사용하여 해당하는 게시물들을 조회합니다.
        List<Post> posts = postRepository.findByPostIdIn(postIds);

        // 조회한 게시물 목록을 모델에 추가합니다.
        model.addAttribute("posts", posts);

        // 사용자 게시물 목록을 보여줄 HTML 템플릿인 "interest"를 반환합니다.
        return "interest";
    }

}