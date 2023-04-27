package com.undefined14.pre.board.question.dto;

import com.undefined14.pre.board.anwser.dto.AnswerResponseDto;
import com.undefined14.pre.board.comment.dto.CommentDto;
import com.undefined14.pre.board.comment.entity.Comment;
import com.undefined14.pre.board.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDto {
    private Long questionId;
    private Long memberId;
    private String title;
    private String problem;
    private String expecting;
    private List<String> tags;
    @NotNull
    private LocalDateTime create_at;
    private Question.QuestionStatus questionStatus;
    private List<CommentDto.Response> comments;
    private List<AnswerResponseDto> answerResponseDto;
}