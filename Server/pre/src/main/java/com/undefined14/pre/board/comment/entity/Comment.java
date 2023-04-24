package com.undefined14.pre.board.comment.entity;

import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "COMMENT")
@NoArgsConstructor
public class Comment {
    // 댓글 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    // 댓글 본문
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    // 댓글 상태
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus = CommentStatus.COMMENT_POSTED;

    // 작성 시간
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 댓글 달린 위치 [QUESTION, ANSWER]
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;


    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Question question;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Answer answer;

    @AllArgsConstructor
    public enum PostType {
        QUESTION,
        ANSWER
    }
    @AllArgsConstructor
    public enum CommentStatus {
        COMMENT_POSTED("등록된 댓글"),
        COMMENT_DELETED("삭제된 댓글");

        @Getter
        private final String status;
    }
}