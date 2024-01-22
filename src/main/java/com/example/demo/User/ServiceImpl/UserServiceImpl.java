package com.example.demo.User.ServiceImpl;
import com.example.demo.Post.Post;
import com.example.demo.Post.PostDTO;
import com.example.demo.Post.PostMapper;
import com.example.demo.Post.PostRepository;
import com.example.demo.User.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserMapper usermapper;
    private final PostMapper postMapper;

    @Transactional
    @Override
    public User registerUser(SignUpDto signUpDto) {
        // userName, phoneNumber 중복 체크
        if(userRepository.existsByUserName(signUpDto.getUserName())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        if(userRepository.existsByPhoneNumber(signUpDto.getPhoneNumber())) {
            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
        }

        //signupdto -> entity 형변환 후 jpa -> 데이터베이스 저장
        return userRepository.save(usermapper.signUpDtoToUser(signUpDto));
    }
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


}
