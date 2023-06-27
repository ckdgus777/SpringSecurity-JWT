package com.example.springsecurityjwt.dto.security;

import com.example.springsecurityjwt.dto.MemberDto;
import com.example.springsecurityjwt.dto.MemberRoleDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record MemberPrincipal(
        String memberId,
        String memberPassword,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

    public static MemberPrincipal of(String memberId, Collection<? extends GrantedAuthority> authorities) {
        return new MemberPrincipal(memberId, "", authorities);
    }

    public static MemberPrincipal from(MemberDto dto) {
        return new MemberPrincipal(
                dto.memberId(),
                dto.memberPassword(),
                dto.memberRoles().stream()
                        .map(MemberRoleDto::role)
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return memberPassword;
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
