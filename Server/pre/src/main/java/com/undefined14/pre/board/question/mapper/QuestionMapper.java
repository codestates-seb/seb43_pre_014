package com.undefined14.pre.board.question.mapper;

import com.undefined14.pre.board.anwser.dto.AnswerResponseDto;
import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.comment.dto.CommentDto;
import com.undefined14.pre.board.comment.entity.Comment;
import com.undefined14.pre.board.question.dto.QuestionPatchDto;
import com.undefined14.pre.board.question.dto.QuestionPostDto;
import com.undefined14.pre.board.question.dto.QuestionResponseAllDto;
import com.undefined14.pre.board.question.dto.QuestionResponseDto;
import com.undefined14.pre.board.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);

    Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);

    default QuestionResponseDto questionToQuestionResponseDto(Question question) {
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        questionResponseDto.setMemberId(question.getMember().getMemberId());
        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setTitle(question.getTitle());
        questionResponseDto.setProblem(question.getProblem());
        questionResponseDto.setExpecting(question.getExpecting());
        questionResponseDto.setCreate_at(question.getCreateAt());
        questionResponseDto.setQuestionStatus(question.getQuestionStatus());
        questionResponseDto.setAnswerResponseDto(new ArrayList<>());
        questionResponseDto.setComments(new ArrayList<>());

        // 답변
        if (question.getAnswer() != null) {
            questionResponseDto.setAnswerResponseDto(question.getAnswer().stream()
                    // 삭제된 답변은 조회 안되게 변경
                    .filter(e -> e.getAnswerStatus() == Answer.AnswerStatus.ANSWER_POSTED)
                    // answer 를 List 에 하나씩 넣어주기 위함
                    .map(answer -> {
                        AnswerResponseDto answerResponseDto = new AnswerResponseDto();
                        answerResponseDto.setAnswerId(answer.getAnswerId());
                        answerResponseDto.setBody(answer.getBody());
                        answerResponseDto.setMemberId(answer.getMember().getMemberId());
                        answerResponseDto.setAnswerStatus(answer.getAnswerStatus().getStatus());
                        answerResponseDto.setComments(answer.getComment().stream()
                                .filter(e -> e.getCommentStatus() == Comment.CommentStatus.COMMENT_POSTED)
                                .map(comment -> new CommentDto.Response(
                                        comment.getCommentId(),
                                        comment.getMember().getMemberId(),
                                        comment.getBody(),
                                        comment.getCreatedAt()
                                )).collect(Collectors.toList()));
                        return answerResponseDto;
                    }).collect(Collectors.toList()));
        }

        // 댓글
        if (question.getComment() != null) {
            questionResponseDto.setComments(question.getComment().stream()
                    // 삭제된 댓글은 조회 안되게 변경
                    .filter(e -> e.getCommentStatus() == Comment.CommentStatus.COMMENT_POSTED)
                    // comment 를 List 에 하나씩 넣어주기 위함
                    .map(comment -> new CommentDto.Response(
                            comment.getCommentId(),
                            comment.getMember().getMemberId(),
                            comment.getBody(),
                            comment.getCreatedAt()
                    )).collect(Collectors.toList()));
        }

        return questionResponseDto;
    }

    List<QuestionResponseDto> questionListToResponseDtoList(List<Question> questions);

    // 전체 조회 시 답변, 댓글 출력 안되게 하기 위함
    default QuestionResponseAllDto questionToQuestionResponseAllDto(Question question) {
        QuestionResponseAllDto questionResponseAllDto = new QuestionResponseAllDto();
        questionResponseAllDto.setMemberId(question.getMember().getMemberId());
        questionResponseAllDto.setQuestionId(question.getQuestionId());
        questionResponseAllDto.setTitle(question.getTitle());
        questionResponseAllDto.setProblem(question.getProblem());
        questionResponseAllDto.setExpecting(question.getExpecting());
        questionResponseAllDto.setCreate_at(question.getCreateAt());
        questionResponseAllDto.setQuestionStatus(question.getQuestionStatus());

        return questionResponseAllDto;
    }

    List<QuestionResponseAllDto> questionListToResponseAllDtoList(List<Question> questions);
}
