package com.example.demo.User.ServiceImpl;
import com.example.demo.User.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper usermapper;

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

        // 사용자 정보를 세션에 저장
        session.setAttribute("user", user);

        // 마지막 활동 시간을 현재 시간으로 갱신
        session.setAttribute("lastActivityTime", LocalDateTime.now());

        return new LoginDto(user.getPhoneNumber());
    }
}