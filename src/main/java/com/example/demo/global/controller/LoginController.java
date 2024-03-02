package com.example.demo.global.controller;

import com.example.demo.global.entity.Token;
import com.example.demo.global.entity.UserEntity;
import com.example.demo.global.security.Repository.JWTUserRepository;
import com.example.demo.global.security.service.JwtService;
import com.example.demo.global.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

import javax.servlet.http.HttpSession;
import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final JwtTokenProvider jwtTokenProvider;
    private final JWTUserRepository jwtUserRepository;
    private final JwtService jwtService;
    private DefaultMessageService messageService;

    @org.springframework.beans.factory.annotation.Value("${cool-sms.api-key}")
    private String apiKey;

    @org.springframework.beans.factory.annotation.Value("${cool-sms.api-secret}")
    private String apiSecret;

    @org.springframework.beans.factory.annotation.Value("${cool-sms.api-url}")
    private String apiUrl;

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, apiUrl);
    }
    //api연동
    @PostMapping("/SendForLogin")
    @ResponseBody
    public ResponseEntity<Object> loginAndSendSMS(@RequestBody Map<String, String> user, HttpSession session) {
        // 휴대폰 번호를 받음
        String phoneNumber = user.get("username");
        log.info("PhoneNumber = {}", phoneNumber);

        // 사용자 검색
        UserEntity member = jwtUserRepository.findByUsername(user.get("username"));

        if (member != null) {
            // 인증번호 생성 및 세션에 인증번호, 인증번호 발급 시각, 사용자 번호 저장
            String numStr = generateRandomNumber();
            session.setAttribute("authNum", numStr);
            session.setAttribute("creationTime", System.currentTimeMillis());
            session.setAttribute("username", phoneNumber);

            log.info("Session attribute - creationTime: {}", session.getAttribute("creationTime"));
            // SMS 전송
            Message message = new Message();
            message.setFrom("01064371608"); // 테스트할 때만 사용
            message.setTo(phoneNumber);
            message.setText("[Ishere] 이즈히어 인증번호는 [" + numStr + "] 입니다.");

            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            log.info("SMS Response: {}", response);

            // 응답에는 필요한 정보를 추가하여 반환
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("status", "success");
            responseMap.put("message", "SMS sent successfully");
            return ResponseEntity.ok(responseMap);
        } else {
            // 사용자가 존재하지 않는 경우
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("status", "error");
            responseMap.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
        }
    }

    @PostMapping("/verifyForLogin")
    @ResponseBody
    public ResponseEntity<Object> verifyAndLogin(@RequestBody Map<String, String> body, HttpSession session) {
        // 세션에 저장된 인증번호와 생성 시간
        String authNum = (String) session.getAttribute("authNum");
        long creationTime = (Long) session.getAttribute("creationTime");
        String phoneNumber = (String) session.getAttribute("username");
        // 사용자가 입력한 인증번호를 요청 본문으로부터 가져옴
        String inputNum = body.get("inputNum");

        // 현재 시간(밀리초 단위)을 가져온 후 대입
        long currentTime = System.currentTimeMillis();

        // 3분이 지났는지 확인
        if (currentTime - creationTime > 3 * 60 * 1000) {
            // 3분이 지났다면 세션에서 인증번호와 생성 시간을 삭제하고 실패 응답 반환
            session.removeAttribute("authNum");
            session.removeAttribute("creationTime");
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("status", "error");
            responseMap.put("message", "Verification expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
        }

        // 사용자가 입력한 인증번호와 세션에 저장된 인증번호가 일치하는지 확인
        boolean isMatch = inputNum.equals(authNum);

        if (isMatch) {
            // 인증번호가 일치하면 세션에서 인증번호와 생성 시간, 사용자 정보를 삭제
            session.removeAttribute("authNum");
            session.removeAttribute("creationTime");
            session.removeAttribute("username");
            // 사용자 검색


            UserEntity member = jwtUserRepository.findByUsername(phoneNumber);

            if (member != null) {
                // 토큰 생성
                Token tokenDto = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRole());
                log.info("getrole = {}", member.getRole());
                jwtService.login(tokenDto);

                // 토큰을 헤더에 추가하여 응답
                return ResponseEntity.ok()
                        .header("Authorization", "Bearer " + tokenDto.getAccessToken())
                        .body(tokenDto);
            } else {
                // 사용자가 존재하지 않는 경우
                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("status", "error");
                responseMap.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMap);
            }
        } else {
            // 인증 실패 시 실패 응답 반환
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("status", "error");
            responseMap.put("message", "Verification failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
        }
    }

    private String generateRandomNumber() {
        Random rand = new Random();
        StringBuilder numStr = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            numStr.append(rand.nextInt(10));
        }
        return numStr.toString();
    }
}