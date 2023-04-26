package com.undefined14.pre.board.anwser.entity;

import com.undefined14.pre.audit.Auditable;
import com.undefined14.pre.board.comment.entity.Comment;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.member.entity.Member;
import lombok.AllArgsConstructor;
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
public class Answer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private AnswerStatus answerStatus = AnswerStatus.ANSWER_POSTED;

    @AllArgsConstructor
    public enum AnswerStatus {
        ANSWER_POSTED("등록된 답변"),
        ANSWER_DELETED("삭제된 답변");

        @Getter
        private String status;
    }

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();
}
