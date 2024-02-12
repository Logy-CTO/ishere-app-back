package com.example.demo.domain.User;

import com.example.demo.domain.Post.Post;
import com.example.demo.domain.Post.PostDTO;
import com.example.demo.domain.Post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    //컨트롤러에서 @ModelAttribute를 사용하면 HTML form에서 전송된 데이터를 받을 수 있습니다.
    // 하지만 JSON 형식의 데이터를 받기 위해서는 @RequestBody 어노테이션을 사용해야 합니다.  //플러터로 변경시 @requestbody로 변경
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SignUpDto registerUser(@RequestBody SignUpDto signUpDto) {
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
    public List<PostDTO> getUserPosts(HttpSession session) {
        return userService.getUserPosts(session);
    }

    @GetMapping("/editProfile")
    public ResponseEntity<String> showEditProfileForm() {
        // 필요한 초기 데이터 로드 등이 있다면 여기서 처리할 수 있습니다.
        return ResponseEntity.ok("Edit Profile Form");
    }

    @PostMapping("/editProfile")
    public ResponseEntity<String> editProfile(@ModelAttribute SignUpDto signUpDto, HttpSession session) {
        userService.updateProfile(signUpDto, session);
        return ResponseEntity.ok("Profile Updated");
    }

}