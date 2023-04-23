package com.undefined14.pre.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member already exists"),
    MEMBER_DELETED(404, "Member already deleted"),
    MEMBER_FORBIDDEN(403,"Member Forbidden"),
    MEMBER_UNAUTHORIZED(401,"UNAUTHORIZED"),
    COMMENT_NOT_FOUND(404, "Comment not found"),;

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
