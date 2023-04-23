package com.undefined14.pre.board.anwser.dto;

import com.undefined14.pre.board.anwser.entity.Answer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class AnswerDto {
    @Getter
    public static class Post {
        @NotNull
        private Long memberId;
        @NotNull
        private String body;
    }
    @Getter
    public static class Patch {
        @NotNull
        private Long memberId;
        @Setter
        private Long answerId;
        private String body;
    }
    @Getter
    @Builder
    public static class Response {
        private Long memberId;
        private Long answerId;
        private String body;
        private String answerStatus;
    }
    @Getter
    public static class Delete {
        private Long memberId;
    }
}
