package com.example.springsecurityjwt.dto;

import com.example.springsecurityjwt.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

public record MemberDto(
        String memberId,
        String memberPassword,
        String memberName,
        LocalDateTime birth,
        String memo,
        List<MemberRoleDto> memberRoles,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long popularity,
        Long unpopularity
) {

    public static MemberDto from(Member entity) {
        return new MemberDto(
                entity.getMemberId(),
                entity.getMemberPassword(),
                entity.getMemberName(),
                entity.getBirth(),
                entity.getMemo(),
                entity.getMemberRoles().stream()
                        .map(MemberRoleDto::from)
                        .toList(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getPopularity(),
                entity.getUnpopularity()
        );
    }

}
