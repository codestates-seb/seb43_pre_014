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
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    //@Column(nullable = false, length = 255)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @CreationTimestamp
    private LocalDateTime created_at;
//
//    @UpdateTimestamp
//    private LocalDateTime updated_at;

    @Enumerated(EnumType.STRING)
    private QuestionStatus status = QuestionStatus.QUESTION_ACTIVE;

    public Question(String title, String body, Member member) {
        this.title = title;
        this.body = body;
        this.member = member;
        this.created_at = LocalDateTime.now();
    }

    public void update(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public void delete() {
        this.status = QuestionStatus.QUESTION_DELETED;
    }

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public enum QuestionStatus {
        QUESTION_ACTIVE("작성 완료"),
        QUESTION_DELETED("삭제 완료");

        @Getter
        private String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }
}
