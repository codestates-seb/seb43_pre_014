package com.undefined14.pre.board.anwser.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class AnswerDto {
    @Getter
    @Setter
    public static class Post {
        @NotNull
        private Long questionId;
        @NotNull
        private String body;
    }
    @Getter
    @Setter
    public static class Patch {
        @Setter
        private Long answerId;
        private String body;
    }
}