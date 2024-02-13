package com.example.demo.global.controller;

import com.example.demo.domain.User.SignUpDto;
import com.example.demo.global.JWT.JWTUtil;
import com.example.demo.global.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    //만든 이유 /login으로도 가능하지만 form-data를 받아야하고 커스텀해서 사용해야 더 직관적임
    //https://substantial-park-a17.notion.site/1-970c48a84eba4c71a4edcdef4c06c757
    //원래 위 링크처럼 운용되는데 내가 직관적으로 변경하였음.. 풀 버전 보고 싶으면 이전 커밋 찾아서 보시길
    // 커밋 명 : jwt 로그인,회원가입 초기 구현, 2.11 JWT 로그인 회원가입 구현 <-- 이 두개
    @PostMapping(value = "/loginuser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {

        String username = loginDTO.getUsername();
        String password = "울트라킹왕짱코딩의신택수";
        System.out.println(username);

        // 사용자 인증 <<-- 여기서 오류 나는데... 이유가 뭘까 해결 : null값 때문이였음..
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, null));
        //authenticationManager 가 userEntitty -> UserRepo -> UserDetailsSer -> UserDetails 를 거쳐 인증을 함
        System.out.println(authentication.getAuthorities());

        //아이디, 비밀번호가 틀린 경우 둘다 BadCredentialsException (보안상의 이유)

        // 사용자의 권한(Role) 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String token = jwtUtil.createJwt(username, role, 30 * 24 * 60 * 60L);

        // 응답에 토큰 추가
        response.addHeader("Authorization", "Bearer " + token);

        // 클라이언트에게 응답 전송
        return ResponseEntity.ok(token);

    }

}