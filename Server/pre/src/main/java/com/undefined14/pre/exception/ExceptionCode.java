package com.undefined14.pre.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member already exists"),
    MEMBER_DELETED(404, "Member already deleted"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    QUESTION_EXISTS(409, "Question already exists"),
    QUESTION_DELETED(404, "Question already deleted"),
    ANSWER_NOT_FOUND(404,"Answer not found"),
    ANSWER_DELETED(404, "Answer already deleted"),
    MEMBER_MISMATCHED(400,"Not Matched Member");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
