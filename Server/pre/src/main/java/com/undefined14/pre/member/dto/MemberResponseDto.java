package com.undefined14.pre.member.dto;

import com.undefined14.pre.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberResponseDto {
    private long memberId;
    private String name;
    private String email;
    private String password;
    private Member.MemberStatus memberStatus;
    private LocalDateTime create_at;
}