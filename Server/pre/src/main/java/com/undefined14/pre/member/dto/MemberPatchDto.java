package com.undefined14.pre.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MemberPatchDto {
    // TODO: 2023-04-18 유효성 검증은 협의 필요

    @Setter // RequestParam 으로 memberId 지정하기 위함 -> 그냥 서비스 계층에서 핸들링해도 무방
            // 안전하게 memberId만 변경 가능하게 애너테이션 적용
    private long memberId;

    private String name;
    private String email;
    private String password;
}
