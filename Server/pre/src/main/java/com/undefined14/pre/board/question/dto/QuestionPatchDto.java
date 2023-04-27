package com.undefined14.pre.board.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPatchDto {
    private Long questionId;
    private String title;
    private String problem;
    private String expecting;
    private List<String> tags;

}
