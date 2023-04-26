package com.undefined14.pre.board.anwser.dto;

import com.undefined14.pre.board.comment.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnswerResponseDto {
    private Long memberId;
    private Long answerId;
    private String body;
    private String answerStatus;
    private List<CommentDto.Response> comments;
}
