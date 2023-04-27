package com.undefined14.pre.member.service;

import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.auth.utils.CustomAuthorityUtils;
import com.undefined14.pre.exception.BusinessLogicException;
import com.undefined14.pre.exception.ExceptionCode;
import com.undefined14.pre.member.entity.Member;
import com.undefined14.pre.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MemberService {
    private MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtTokenizer jwtTokenizer;

    public Member createMember(Member member) {

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        if (member.getName() == null) {
            member.setName(member.getEmail());
        }

        if (member.getNews() == null) {
            member.setNews(false);
        }

        return memberRepository.save(member);
    }

    public Member updateMember(Member member, String token) {

        long memberId = jwtTokenizer.getMemberId(token);

        Member findMember = findVerifiedMember(memberId);

        Optional.ofNullable(member.getName())
                .ifPresent(findMember::setName);
        Optional.ofNullable(member.getEmail())
                .ifPresent(findMember::setEmail);
        Optional.ofNullable(member.getPassword())
                .ifPresent(findMember::setPassword);
        Optional.ofNullable(member.getNews())
                .ifPresent(findMember::setNews);

            //findMember.setModifiedAt(LocalDateTime.now()); 수정날짜 표기

        return memberRepository.save(findMember);
    }

    public Member findMember(String token) {

        long memberId = jwtTokenizer.getMemberId(token);

        Member findMember = findVerifiedMember(memberId);

        if(findMember.getMemberStatus().equals(Member.MemberStatus.MEMBER_QUIT)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        return findMember;
    }

    public void deleteMember(String token) {

        long memberId = jwtTokenizer.getMemberId(token);

        Member findMember = findVerifiedMember(memberId);

        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);

        memberRepository.save(findMember);
    }

    private Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        return optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
