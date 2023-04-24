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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    // 형식을 TEXT로 -> 훨씬 긺
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus = CommentStatus.COMMENT_POSTED;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // default-> 질문에 달린 댓글
    // postType 대용
    @Column(nullable = false)
    private boolean isInheritQuestion;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, name = "post_type")
//    private PostType postType;

    // 댓글입장에서 Many???
    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Question question;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Answer answer;

//    public enum PostType {
//        QUESTION,
//        ANSWER
//    }

    @AllArgsConstructor
    public enum CommentStatus {
        COMMENT_POSTED("등록된 댓글"),
        COMMENT_DELETED("삭제된 댓글");

        @Getter
        private String status;
    }
}