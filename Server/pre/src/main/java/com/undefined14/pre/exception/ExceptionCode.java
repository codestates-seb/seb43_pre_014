package com.undefined14.pre.exception;

import lombok.Getter;

public enum ExceptionCode {
    //Member & Auth Exception
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member already exists"),
    MEMBER_MISMATCHED(400,"Not Matched Member"),
    MEMBER_FORBIDDEN(403,"Member Forbidden"),
    MEMBER_UNAUTHORIZED(401,"UNAUTHORIZED"),

    //Question Exception
    QUESTION_NOT_FOUND(404, "Question not found"),
    QUESTION_EXISTS(409, "Question already exists"),
    QUESTION_DELETED(404, "Question already deleted"),

    //Answer Exception
    ANSWER_NOT_FOUND(404,"Answer not found"),
    ANSWER_DELETED(404, "Answer already deleted"),


    //Comment Exception
    COMMENT_NOT_FOUND(404, "Comment not found"),
    COMMENT_DELETED(404, "Comment already deleted")
    ;

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
