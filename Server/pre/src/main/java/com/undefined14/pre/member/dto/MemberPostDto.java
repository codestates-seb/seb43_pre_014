package com.undefined14.pre.member.dto;

import lombok.Getter;

@Getter
public class MemberPostDto {
    // TODO: 2023-04-18 유효성 검증은 협의 필요  
    private String name;
    private String email;
    private String password;
}
