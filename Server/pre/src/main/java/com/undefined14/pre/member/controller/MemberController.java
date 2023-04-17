package com.undefined14.pre.member.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@Validated
@AllArgsConstructor
@Slf4j // 통신 로그 위함
public class MemberController {
    private final MemberMapper mapper;
    private final MemberService service;

    // 회원 가입
    @PostMapping
    public ResponseEntity postMember(@Validated @RequestBody MemberPostDto memberPostDto) {

        log.info(memberPostDto);

        Member member = mapper.MemberPostDtoToMember(memberPostDto);
        Member response = service.createMember(member);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 회원 정보 수정
    @PatchMapping

    // 회원 정보 조회
    @GetMapping

    // 회원 정보 삭제 -> status 만 변경
    @DeleteMapping
}