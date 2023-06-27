package com.example.springsecurityjwt.dto.request;

public record LoginRequest(
        String memberId,
        String memberPassword
) {
}
