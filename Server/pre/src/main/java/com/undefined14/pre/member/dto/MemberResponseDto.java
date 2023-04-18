package com.undefined14.pre.member.dto;

import com.undefined14.pre.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private long memberId;
    private String name;
    private String email;
    private String password;
    private Member.MemberStatus memberStatus;
}