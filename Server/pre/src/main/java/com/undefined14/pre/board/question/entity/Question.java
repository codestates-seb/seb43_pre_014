package com.undefined14.pre.board.question.entity;

import com.undefined14.pre.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(nullable = false, length = 100)
    private String title;

    //@Column(nullable = false, length = 255)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    private LocalDateTime create_at;

    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_ACTIVE;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public enum QuestionStatus {
        QUESTION_ACTIVE("작성 완료"),
        QUESTION_DELETED("삭제 완료");

        @Getter
        private final String status;

        QuestionStatus(String status) {
            this.status = status;
        }

    }

    public Question (String title, String body, Member member) {
        this.title = title;
        this.body = body;
        this.member = member;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getQuestions().add(this);
    }

}