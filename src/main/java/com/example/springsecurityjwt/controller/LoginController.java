package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.component.JwtAuthenticationFilter;
import com.example.springsecurityjwt.dto.request.LoginRequest;
import com.example.springsecurityjwt.dto.response.LoginResponse;
import com.example.springsecurityjwt.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse jwtToken = loginService.authenticate(loginRequest.memberId(), loginRequest.memberPassword());

        // response header와 응답 객체 모두에 넣는다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER,
                JwtAuthenticationFilter.BEARER_TYPE + jwtToken.accessToken());

        return new ResponseEntity<>(jwtToken, httpHeaders, HttpStatus.OK);
    }

}
