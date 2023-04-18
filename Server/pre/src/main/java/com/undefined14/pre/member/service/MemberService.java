package com.undefined14.pre.member.service;

import com.undefined14.pre.member.entity.Member;
import com.undefined14.pre.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    public Member createMember(Member member) {

        return memberRepository.save(member);
    }

    public Member updateMember(Member member) {

        Member findMember = findVerifiedMember(member.getMemberId());

        // TODO: 2023-04-18 내용 수정 로직 필요

        return memberRepository.save(updatedMember);
    }

    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findmember);
    }

    private Member findVerifiedMember(Member member) {
        // TODO: 2023-04-18  DB에서 멤버 id로 멤버 찾아와야 합니다.
    }
}
