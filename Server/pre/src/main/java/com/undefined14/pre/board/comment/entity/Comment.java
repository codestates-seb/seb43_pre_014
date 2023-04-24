package com.undefined14.pre.board.comment.entity;

import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.member.entity.Member;
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

    @Column(nullable = false, columnDefinition = "TEXT", length = 255)
    private String body;

//    @Enumerated(EnumType.STRING)
//    private CommentStatus commentStatus = CommentStatus.COMMENT_ACTIVE;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // default-> 질문에 달린 댓글
    // postType 대용
    @Column(nullable = false)
    private boolean isInheritQuestion;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, name = "post_type")
//    private PostType postType;

    @ManyToOne
    @JoinColumn(name = "WRITER_ID", nullable = false)
    private Member writer;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Answer answer;

//    public enum PostType {
//        QUESTION,
//        ANSWER
//    }

//    @AllArgsConstructor
//    public enum CommentStatus {
//        COMMENT_ACTIVE("등록된 댓글"),
//        COMMENT_DELETED("삭제된 댓글");
//
//        @Getter
//        private String status;
//    }
}