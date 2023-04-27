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
    private Member.MemberStatus memberStatus;
    private Boolean news;
    private String img;
    private LocalDateTime create_at;
}