package com.undefined14.pre.board.question.dto;

import com.undefined14.pre.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class QuestionPostDto {
    private String title;
    private String problem;
    private String expecting;
}
