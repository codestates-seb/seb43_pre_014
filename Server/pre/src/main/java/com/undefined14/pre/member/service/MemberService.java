package com.undefined14.pre.member.service;

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

    @Transactional
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {

        Member findMember = findVerifiedMember(member.getMemberId());
        Optional.ofNullable(member.getName())
                .ifPresent(findMember::setName);
        Optional.ofNullable(member.getEmail())
                .ifPresent(findMember::setEmail);
        Optional.ofNullable(member.getPassword())
                .ifPresent(findMember::setPassword);

        if (member.getNews()) {
            findMember.setNews(true);
        }
        if (!member.getNews()) {
            findMember.setNews(false);
        }

        //findMember.setModifiedAt(LocalDateTime.now()); 수정날짜 표기

        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);

        if(findMember.getMemberStatus().equals(Member.MemberStatus.MEMBER_QUIT)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        return findMember;
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);

        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);

        memberRepository.save(findMember);
    }

    @Transactional
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        return optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
