package com.undefined14.pre.board.question.dto;

import com.undefined14.pre.board.question.entity.Question;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class QuestionResponseDto {
    private Long questionId;
    private Long memberId;
    private String title;
    private String body;
    @NotNull
    private LocalDateTime create_at;
    private Question.QuestionStatus questionStatus;

    public QuestionResponseDto(Long questionId, Long memberId, String title, String body, LocalDateTime create_at, Question.QuestionStatus questionStatus) {
        this.questionId = questionId;
        this.memberId = memberId;
        this.title = title;
        this.body = body;
        this.create_at = create_at;
        this.questionStatus = questionStatus;
    }
}
