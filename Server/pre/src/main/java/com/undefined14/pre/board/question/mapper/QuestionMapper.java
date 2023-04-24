package com.undefined14.pre.board.question.mapper;

import com.undefined14.pre.board.anwser.dto.AnswerResponseDto;
import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.question.dto.QuestionPatchDto;
import com.undefined14.pre.board.question.dto.QuestionPostDto;
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
                        return answerResponseDto;
                    }).collect(Collectors.toList())
            );
        }

        return questionResponseDto;
    }

    List<QuestionResponseDto> questionListToResponseDtoList(List<Question> questions);
}
