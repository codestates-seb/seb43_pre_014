package com.undefined14.pre.member.repository;

import com.undefined14.pre.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
