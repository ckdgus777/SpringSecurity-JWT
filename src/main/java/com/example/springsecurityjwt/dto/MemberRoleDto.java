package com.example.springsecurityjwt.dto;

import com.example.springsecurityjwt.domain.MemberRole;

public record MemberRoleDto(
        String memberId,
        String role
) {

    public static MemberRoleDto from(MemberRole entity) {
        return new MemberRoleDto(
                entity.getMember().getMemberId(),
                entity.getRole()
        );
    }

}
