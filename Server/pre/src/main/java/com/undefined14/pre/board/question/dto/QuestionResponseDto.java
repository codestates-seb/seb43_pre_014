package com.undefined14.pre.board.question.dto;

import com.undefined14.pre.board.anwser.dto.AnswerResponseDto;
import com.undefined14.pre.board.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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

    List<AnswerResponseDto> answerResponseDto;
}
