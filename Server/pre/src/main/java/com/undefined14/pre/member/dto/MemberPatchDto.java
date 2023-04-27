package com.undefined14.pre.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
public class MemberPatchDto {
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;
    private Boolean news;

}
