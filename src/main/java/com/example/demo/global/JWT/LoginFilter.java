package com.example.demo.global.JWT;

import com.example.demo.global.dto.CustomUserDetails;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;


// 클라이언트 요청 -> 톰캣 진입 -> 일련의 필터들 -> 스프링 서블릿(컨트롤러) 와 같은 방식으로 요청이 이루어집니다.
//
// 이때 스프링 시큐리티는 일련의 필터들에서 사용자의 요청을 검증하는데 그 중 저희가 만든 LoginFilter는 UsernamePasswordAuthenticationFilter의
// 정의에 따라서 /login 경로로 오는 POST 요청을 검증하게 됩니다.
// 따라서 필터단에서 해당 요청을 캐치하여 검증을 진행하고 응답하기 때문에 컨트롤러에서 처리할 필요가 없습니다.
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println(username);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        //10시간 유지
        //String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);
        //한달 유지
        String token = jwtUtil.createJwt(username, role, 30 * 24 * 60 * 60L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }
}