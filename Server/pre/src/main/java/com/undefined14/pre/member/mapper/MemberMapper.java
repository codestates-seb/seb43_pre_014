package com.undefined14.pre.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post requestBody);
    Member memberPatchDtoToMember(MemberDto.Patch requestBody);
    MemberResponseDto memberToMemberResponseDto(Member member);
    //List<MemberDto.Response> membersToMemberResponseDtos(List<Member> members);

}
