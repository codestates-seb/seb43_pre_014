package com.undefined14.pre.board.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionPatchDto {
    private Long questionId;
    private String title;
    private String problem;
    private String expecting;
}
