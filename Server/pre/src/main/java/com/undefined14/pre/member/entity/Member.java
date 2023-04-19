package com.undefined14.pre.member.entity;

import com.undefined14.pre.audit.Auditable;
import lombok.Getter;

import javax.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memberId;

    @Column(nullable = false, unique = true, length = 25)
    private String name;

    @Column(nullable = false, unique = true, length = 25)
    private String email;

    @Column(nullable = false, length = 25)
    private String password;

    // 회원 상태 값 (기본 활동 중)
    @Column(nullable = false, length = 10)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    // 회원 활통 상태
    public enum MemberStatus {
        MEMBER_ACTIVE("활동 중"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

    // TODO: 2023-04-18  JPA 엔티티 연관 관계 매핑을 이 아래부터...
}
