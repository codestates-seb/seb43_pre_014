package com.undefined14.pre.board.anwser.mapper;

import com.undefined14.pre.board.anwser.dto.AnswerDto;
import com.undefined14.pre.board.anwser.dto.AnswerResponseDto;
import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.comment.dto.CommentDto;
import com.undefined14.pre.board.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {

    @Mapping(source = "memberId", target = "member.memberId")
    @Mapping(source = "questionId", target = "question.questionId")
    Answer answerPostDtoToAnswer(AnswerDto.Post answerPostDto);

    @Mapping(source = "memberId", target = "member.memberId")
    Answer answerPatchDtoToAnswer(AnswerDto.Patch answerPatchDto);

    @Mapping(source = "memberId", target = "member.memberId")
    Answer answerDeleteDtoToAnswer(AnswerDto.Delete answerDeleteDto);

    default AnswerResponseDto answerToAnswerResponseDto(Answer answer) {
        AnswerResponseDto answerResponseDto = new AnswerResponseDto();
        answerResponseDto.setAnswerId(answer.getAnswerId());
        answerResponseDto.setMemberId(answer.getMember().getMemberId());
        answerResponseDto.setBody(answer.getBody());
        answerResponseDto.setAnswerStatus(answer.getAnswerStatus().getStatus());

        if (answer.getComment() != null) {
            answerResponseDto.setComments(answer.getComment().stream()
                    .filter(e -> e.getCommentStatus() == Comment.CommentStatus.COMMENT_POSTED)
                    .map(comment -> new CommentDto.Response(
                            comment.getCommentId(),
                            comment.getMember().getMemberId(),
                            comment.getBody(),
                            comment.getCreatedAt()
                    )).collect(Collectors.toList()));
        }

        return answerResponseDto;
    }
}
