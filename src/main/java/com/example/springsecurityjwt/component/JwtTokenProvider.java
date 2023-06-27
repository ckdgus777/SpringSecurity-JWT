package com.example.springsecurityjwt.component;

import com.example.springsecurityjwt.dto.security.MemberPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-validity-times}")
    private Long accessTokenValidityTimes;

    private static final String AUTHORITIES_KEY = "authRole";

    // secret 으로 Key 생성
    private Key getSecretKey() {
        byte[] KeyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(KeyBytes);
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String jwtToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        // UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(claims.getSubject());
        MemberPrincipal memberPrincipal = MemberPrincipal.of(claims.getSubject(), authorities);
        return new UsernamePasswordAuthenticationToken(memberPrincipal.getUsername(), null, memberPrincipal.getAuthorities());
    }

    // JWT 생성
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date expiration = new Date(now + this.accessTokenValidityTimes);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .setExpiration(expiration)
                .compact();
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch(ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
        } catch(SignatureException e) {
            log.warn("서명 검증에 실패했습니다.");
        } catch(Exception e) {
            log.warn("잘못된 JWT 토큰입니다.");
        }
        return false;
    }

}
