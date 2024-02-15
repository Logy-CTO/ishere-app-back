package com.example.demo.domain.User.ServiceImpl;
import com.example.demo.domain.Post.Post;
import com.example.demo.domain.Post.PostDTO;
import com.example.demo.domain.Post.PostMapper;
import com.example.demo.domain.Post.PostRepository;
import com.example.demo.domain.User.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserMapper usermapper;
    private final PostMapper postMapper;

    @Transactional
    @Override
    public User registerUser(@RequestBody SignUpDto signUpDto) {
        // userName, phoneNumber 중복 체크
        if(userRepository.existsByUserName(signUpDto.getUserName())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        if(userRepository.existsByPhoneNumber(signUpDto.getPhoneNumber())) {
            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
        }

        // ** usermapper에서 username,phonenumber를 넣어줘서 따로 선언 x
        User user = usermapper.signUpDtoToUser(signUpDto); // signUpDto를 User로 형변환합니다.

        user.setPassword(bCryptPasswordEncoder.encode("울트라킹왕짱코딩의신택수")); // User Entity에 암호화된 패스워드를 설정합니다.
        user.setRole("ROLE_ADMIN"); // 권한 일시 부여

        // Entity를 데이터베이스에 저장하고, 결과를 반환합니다.
        return userRepository.save(user);
    }
//밑에는 세션이라 사용 x
    @Transactional
    @Override
    public LoginDto loginUser(LoginDto loginDto, HttpSession session) {
        if (session == null) {
            throw new IllegalArgumentException("세션이 유효하지 않습니다.");
        }

        User user = userRepository.findByPhoneNumber(loginDto.getPhoneNumber());
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        //세션을 한달간 유지
        session.setMaxInactiveInterval(60 * 60 * 24 * 30);
        // 로그인 성공 시 세션에 사용자 ID 저장
        session.setAttribute("userId", user.getId());
        // 사용자 정보를 세션에 저장
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("userId", user.getId());
        // 마지막 활동 시간을 현재 시간으로 갱신
        session.setAttribute("lastActivityTime", LocalDateTime.now());

        return new LoginDto(user.getPhoneNumber());
    }
//여기까지 사용 x
    @Override
    public List<PostDTO> getUserPosts(HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");

        if (loggedInUserId == null) {
            // 예외 처리 등을 추가해도 좋습니다.
            return Collections.emptyList();
        }

        User user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        String interestPost = String.valueOf(user.getInterestPost());

        List<String> postIdStrings = Arrays.asList(interestPost.split(","));

        List<Integer> postIds = postIdStrings.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Post> postEntities = postRepository.findByPostIdIn(postIds);

        // Post를 PostDTO로 변환합니다.
        return postEntities.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateProfile(SignUpDto signUpDto, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");

        if (loggedInUserId == null) {
            // 로그인되지 않은 사용자 처리
            throw new RuntimeException("User not logged in.");
        }

        User existingUser = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 업데이트할 필드들만 변경
        existingUser.setRealName(signUpDto.getRealName());
        existingUser.setUserName(signUpDto.getUserName());
        existingUser.setBankName(signUpDto.getBankName());
        existingUser.setAccountNumber(signUpDto.getAccountNumber());

        // 변경된 정보를 저장
        userRepository.save(existingUser);
    }

}
