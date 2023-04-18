package com.undefined14.pre.auth.authDto;

import lombok.Getter;
// 클라이언트가 전송한 로그인 정보를 Security Filter에서 사용할 수 있도록 역직렬화하기 위한 dto
@Getter
public class LoginDto {
    private String username;
    private String password;
}
