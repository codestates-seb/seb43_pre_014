package com.undefined14.pre.board.anwser.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class AnswerDto {
    @Getter
    public static class Post {
        @NotNull
        private Long memberId;
        @NotNull
        private Long questionId;
        @NotNull
        private String body;
    }
    @Getter
    public static class Patch {
        @NotNull
        private Long memberId;
        @NotNull
        private Long questionId;
        @Setter
        private Long answerId;
        private String body;
    }
    @Getter
    public static class Delete {
        private Long memberId;
    }
}
