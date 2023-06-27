package com.example.springsecurityjwt.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class Member extends AuditingFields {

    @Id
    @Column(length = 50)
    private String memberId;

    @Setter @Column(nullable = false) private String memberPassword;
    @Setter @Column(nullable = false) private String memberName;
    @Setter @Column(nullable = false) private LocalDateTime birth;
    @Setter private String memo;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<MemberRole> memberRoles = new ArrayList<>();

    protected Member() { }

    private Member(String memberId, String memberPassword, String memberName, LocalDateTime birth, String memo) {
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.birth = birth;
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member that)) return false;
        return memberId != null && memberId.equals(that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}
