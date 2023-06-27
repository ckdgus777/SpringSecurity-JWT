package com.example.springsecurityjwt.repository;

import com.example.springsecurityjwt.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
