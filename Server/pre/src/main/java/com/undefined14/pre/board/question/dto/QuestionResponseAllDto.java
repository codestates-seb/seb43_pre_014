package com.undefined14.pre.board.question.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
public class QuestionResponseAllDto {
    private Long questionId;
    private Long memberId;
    private String title;
    private String problem;
    private String expecting;
    private List<String> tags;
    @NotNull
    private LocalDateTime create_at;
    private Question.QuestionStatus questionStatus;
}
