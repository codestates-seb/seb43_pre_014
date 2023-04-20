package com.undefined14.pre.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
public class MemberPatchDto {

    @Setter // RequestParam 으로 memberId 지정하기 위함 -> 그냥 서비스 계층에서 핸들링해도 무방
            // 안전하게 memberId만 변경 가능하게 애너테이션 적용
    private long memberId;

    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;
}
