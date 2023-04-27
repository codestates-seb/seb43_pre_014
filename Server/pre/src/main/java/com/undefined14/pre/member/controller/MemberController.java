package com.undefined14.pre.member.controller;

import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.member.dto.MemberPatchDto;
import com.undefined14.pre.member.dto.MemberPostDto;
import com.undefined14.pre.member.entity.Member;
import com.undefined14.pre.member.mapper.MemberMapper;
import com.undefined14.pre.member.service.MemberService;
import com.undefined14.pre.util.UriCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "X-AUTH-TOKEN")
@RestController
@RequestMapping("/members")
@Validated
@AllArgsConstructor
@Slf4j // 통신 로그 위함
public class MemberController {

    private final static String MEMBER_DEFAULT_URL = "/members";
    private final MemberMapper mapper;
    private final MemberService service;

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity postMember(@Validated @RequestBody MemberPostDto memberPostDto) {

        log.info(String.valueOf(memberPostDto));

        Member response = service.createMember(mapper.memberPostDtoToMember(memberPostDto));


        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, response.getMemberId());
        return ResponseEntity.created(location).build();
    }

    // 회원 정보 수정
    @PatchMapping("/editprofile")
    public ResponseEntity patchMember(@RequestHeader(name = "Authorization") String token,
                                      @Validated @RequestBody MemberPatchDto memberPatchDto) {

        log.info(String.valueOf(memberPatchDto));

        Member response = service.updateMember(mapper.memberPatchDtoToMember(memberPatchDto),token);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원 정보 조회
    @GetMapping
    public ResponseEntity getMember(@RequestHeader(name = "Authorization") String token) {

        Member response = service.findMember(token);

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.OK);
    }

    // 회원 정보 삭제 -> status 만 변경
    // 리턴 값이 void 이기 때문에 @ResponseStatus 사용
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@RequestHeader(name = "Authorization") String token) {

        service.deleteMember(token);
    }
}