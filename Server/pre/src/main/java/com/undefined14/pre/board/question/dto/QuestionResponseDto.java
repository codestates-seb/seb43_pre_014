package com.undefined14.pre.board.question.dto;

import com.undefined14.pre.board.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionResponseDto {
    private Long questionId;
    private Long memberId;
    private String title;
    private String body;
    @NotNull
    private LocalDateTime create_at;
    private Question.QuestionStatus questionStatus;

}
