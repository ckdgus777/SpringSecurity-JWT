package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.component.JwtTokenProvider;
import com.example.springsecurityjwt.dto.MemberDto;
import com.example.springsecurityjwt.dto.response.LoginResponse;
import com.example.springsecurityjwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    // memberId와 memberPassword로 사용자를 인증한 후, 엑세스 토큰을 반환합니다.
    public LoginResponse authenticate(String username, String password) {

        // memberId, memberPassword 를 사용하여 해당 유저 확인
        MemberDto memberDto = memberRepository.findById(username)
                .map(MemberDto::from)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다 - username : " + username));

        if (!passwordEncoder.matches(password, memberDto.memberPassword())) {
            log.warn("잘못된 비밀번호 입니다.");
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        log.info("비밀번호 통과");

        // UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // authenticationToken을 사용하여 Authentication 객체 생성
        // 이 과정에서 CustomUserDetailsService 에서 우리가 재정의한 loadUserByUsername 메서드 호출
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기준으로 JwtAccessToken 생성
        String accessToken = jwtTokenProvider.createToken(authentication);

        return LoginResponse.of(accessToken);
    }

}
