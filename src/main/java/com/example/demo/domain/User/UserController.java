package com.example.demo.domain.User;

import com.example.demo.domain.Post.Post;
import com.example.demo.domain.Post.PostDTO;
import com.example.demo.domain.Post.PostRepository;
import com.example.demo.global.security.principal.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<User> registerUser(@RequestBody SignUpDto signUpDto) {
        User registeredUser = userService.registerUser(signUpDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/check-pin")
    public ResponseEntity<String> checkPinNumber(@RequestBody SignUpDto request) {
        boolean isValid = userService.checkPinNumber(request.getPhoneNumber(), request.getPinNumber());
        if (isValid) {
            return ResponseEntity.ok("Valid Pin Number");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Pin Number");
        }
    }

    //사용자 마이페이지에서 개인정보 수정(닉네임, 은행계좌 정보 -> 나중에 분리해야할듯)
    @PostMapping("/update")
    public ResponseEntity<User> updateProfile(@RequestBody SignUpDto signUpDto) {
        // SecurityContext에서 Authentication 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 진행해주세요");
        }

        // 인증된 사용자의 정보를 CustomUserDetails로 캐스팅
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        //customUserDetails.getUsername() -> PhoneNumber임
        signUpDto.setPhoneNumber(customUserDetails.getUsername());

        // 사용자 프로필 업데이트
        User updatedUser = userService.updateProfile(signUpDto);
        return ResponseEntity.ok(updatedUser);
    }


    /*** 세션방식 사용안함
     * @GetMapping("/interest")
     *     public List<PostDTO> getUserPosts() {
     *         return userService.getUserPosts();
     *     }



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
*/
}