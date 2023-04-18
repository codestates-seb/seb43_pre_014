package com.undefined14.pre.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class MemberMapper {
    Member memberPostDtoToMember(MemberDto.post requestBody);
    Member memberPatchDtoToMember(MemberDto.Patch requestBody);

}
