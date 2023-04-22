package com.undefined14.pre.board.question.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionPatchDto {
    private Long questionId;
    private Long memberId;
    private String title;
    private String body;
}
