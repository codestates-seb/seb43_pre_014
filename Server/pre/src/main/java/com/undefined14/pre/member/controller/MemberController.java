package com.undefined14.pre.member.controller;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;

@CrossOrigin(origins = "http://localhost:3000")
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
    @PostMapping
    public ResponseEntity postMember(@Validated @RequestBody MemberPostDto memberPostDto) {
            log.info(String.valueOf(memberPostDto));

            Member response = service.createMember(mapper.memberPostDtoToMember(memberPostDto));

            URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, response.getMemberId());
            return ResponseEntity.created(location).build();
    }

    // 회원 정보 수정
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Validated @RequestBody MemberPatchDto memberPatchDto) {

        memberPatchDto.setMemberId(memberId);

        log.info(String.valueOf(memberPatchDto));

        Member response = service.updateMember(mapper.memberPatchDtoToMember(memberPatchDto));

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response),HttpStatus.OK);
    }

    // 회원 정보 조회
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {

        log.info(String.valueOf(memberId));

        Member response = service.findMember(memberId);

        return new ResponseEntity<>(mapper.memberToMemberResponseDto(response), HttpStatus.OK);
    }

    // 회원 정보 삭제 -> status 만 변경
    // 리턴 값이 void 이기 때문에 @ResponseStatus 사용
    @DeleteMapping("/{member-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable("member-id") @Positive long memberId) {
        log.info(String.valueOf(memberId));

        service.deleteMember(memberId);
    }
}