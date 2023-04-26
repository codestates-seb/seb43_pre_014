package com.undefined14.pre.board.question.entity;

import com.undefined14.pre.audit.Auditable;
import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.comment.entity.Comment;
import com.undefined14.pre.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String problem;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String expecting;
    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_ACTIVE;
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "question_tags",
            joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "tag")
    @org.hibernate.annotations.OrderBy(clause = "tag asc")
    @OrderColumn(name = "index")
    private Set<String> tags = new LinkedHashSet<>();


    public enum QuestionStatus {
        QUESTION_ACTIVE("작성 완료"),
        QUESTION_DELETED("삭제 완료");

        @Getter
        private final String status;

        QuestionStatus(String status) {
            this.status = status;
        }

    }
    public Question (String title, String problem, String expecting, Member member, Set<String> tags) {
        this.title = title;
        this.problem = problem;
        this.expecting = expecting;
        this.member = member;
        this.tags = tags;
    }
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answer = new ArrayList<>();
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();
}