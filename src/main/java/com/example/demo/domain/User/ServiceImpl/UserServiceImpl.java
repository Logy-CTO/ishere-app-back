package com.example.demo.domain.User.ServiceImpl;
import com.example.demo.domain.Post.repository.PostRepository;
import com.example.demo.domain.User.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserMapper userMapper;

    //회원가입
    @Transactional
    public User registerUser(SignUpDto signUpDto) {
        // userName, phoneNumber 중복 체크
        if (userRepository.existsByUserName(signUpDto.getUserName())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        if (userRepository.existsByPhoneNumber(signUpDto.getPhoneNumber())) {
            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
        }

        // User Entity 생성 후 저장
        User user = userMapper.signUpDtoToUser(signUpDto);
        return userRepository.save(user);
    }

    //글쓰기 사용자 닉네임 지정
    public String findUserNameByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return user.getUserName();
    }
    //글쓰기 사용자 지역 지정
    public String findAreaNameByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return user.getAreaName();
    }

    //핀번호(결제)
    @Override
    public boolean checkPinNumber(String phoneNumber, String pinNumber) {
        // 사용자 이름으로 사용자를 검색합니다.
        User user = userRepository.findByPhoneNumber(phoneNumber);

        // 사용자가 존재하지 않거나 핀 번호가 null이면 false를 반환합니다.
        if (user == null || user.getPinNumber() == null) {
            return false;
        }

        // 저장된 핀 번호와 입력된 핀 번호를 비교하여 일치하는지 확인하고 결과를 반환합니다.
        return bCryptPasswordEncoder.matches(pinNumber, user.getPinNumber());
    }

    //마이페이지 업데이트, user에서 setter를 사용하지 않고 메서드로 수정
    @Override
    @Transactional
    public User updateProfile(SignUpDto signUpDto) {
        User user = userRepository.findByPhoneNumber(signUpDto.getPhoneNumber());

        if (userRepository.existsByUserName(signUpDto.getUserName())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        user.updateUserName(signUpDto.getUserName());
        user.updateBankName(signUpDto.getBankName());
        user.updateRealName(signUpDto.getRealName());
        user.updateAccountNumber(signUpDto.getAccountNumber());

        return userRepository.save(user);
    }

    //사용자의 지역 변경(게시판 변경)
    public void updateArea(String phoneNumber, UserAreaDto userAreaDto){
        User user = userRepository.findByPhoneNumber(phoneNumber);

        user.updateArea(userAreaDto.getAreaName());
        userRepository.save(user);

    }


}