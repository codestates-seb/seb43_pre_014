package com.undefined14.pre.member.mapper;

import com.undefined14.pre.member.dto.MemberPatchDto;
import com.undefined14.pre.member.dto.MemberPostDto;
import com.undefined14.pre.member.dto.MemberResponseDto;
import com.undefined14.pre.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    // TODO: 2023-04-18 추후 JPA 엔티티 연관관계 매핑 구현 시 수동 매핑 or 애너테이션 매핑해야 할 수 있음

    Member memberPostDtoToMember(MemberPostDto memberPostDto);

    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);

    MemberResponseDto memberToMemberResponseDto(MemberResponseDto memberResponseDto);
}
