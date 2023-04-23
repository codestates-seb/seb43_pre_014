package com.undefined14.pre.board.anwser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerResponseDto {
    private Long memberId;
    private Long answerId;
    private String body;
    private String answerStatus;
}
