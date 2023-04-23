package com.undefined14.pre.member.entity;

import com.undefined14.pre.audit.Auditable;
import com.undefined14.pre.board.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memberId;

    @Column(nullable = false, unique = true, length = 25)
    private String name;

    @Column(nullable = false, unique = true, length = 25)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column
    private Boolean news;

    // 회원 상태 값 (기본 활동 중)
    @Enumerated(value = EnumType.STRING)
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

    @ElementCollection(fetch = FetchType.EAGER) // 해당 에너테이션을 사용해 사용자 등록 시, 사용자의 권한을 등록하기 위한 권한 테이블 생성
    private List<String> roles= new ArrayList<>();

    // TODO: 2023-04-18  JPA 엔티티 연관 관계 매핑을 이 아래부터...
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Question> questions = new ArrayList<>();
//    public List<Question> getQuestions() {
//        return this.questions;
//    }
}
