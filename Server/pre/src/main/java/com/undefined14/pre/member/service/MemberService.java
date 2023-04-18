package com.undefined14.pre.member.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {
    public MemberService(MemberRepository memberRepository, CustombeanUtils<Member> beanUtils) {
        this.memberRepository = memberRepository;
        this.beanUtils = beanUtils;
    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());

        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Member updatedMember = beanUtils.copyNonNullProperties(member, findMember);

        return memberRepository.save(updatedMember);
    }

    public Member findMember(long memberId) {
        return findVerfiedMember(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(pageRequest.of(page, size, sort.by("memberId").descending()));
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findmember);
    }


}
