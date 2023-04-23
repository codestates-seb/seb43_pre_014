package com.undefined14.pre.board.anwser.mapper;

import com.undefined14.pre.board.anwser.dto.AnswerDto;
import com.undefined14.pre.board.anwser.dto.AnswerResponseDto;
import com.undefined14.pre.board.anwser.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {

    @Mapping(source = "memberId", target = "member.memberId")
    @Mapping(source = "questionId", target = "question.questionId")
    Answer answerPostDtoToAnswer(AnswerDto.Post answerPostDto);

    @Mapping(source = "memberId", target = "member.memberId")
    Answer answerPatchDtoToAnswer(AnswerDto.Patch answerPatchDto);

    @Mapping(source = "memberId", target = "member.memberId")
    Answer answerDeleteDtoToAnswer(AnswerDto.Delete answerDeleteDto);

    @Mapping(source = "answerStatus.status", target = "answerStatus")
    @Mapping(source = "member.memberId", target = "memberId")
    AnswerResponseDto answerToAnswerResponseDto(Answer answer);
}
