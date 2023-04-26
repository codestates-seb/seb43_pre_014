package com.undefined14.pre.board.question.mapper;

import com.undefined14.pre.board.question.dto.QuestionPatchDto;
import com.undefined14.pre.board.question.dto.QuestionPostDto;
import com.undefined14.pre.board.question.dto.QuestionResponseAllDto;
import com.undefined14.pre.board.question.dto.QuestionResponseDto;
import com.undefined14.pre.board.question.entity.Question;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-25T12:29:05+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public Question questionPostDtoToQuestion(QuestionPostDto questionPostDto) {
        if ( questionPostDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setTitle( questionPostDto.getTitle() );
        question.setProblem( questionPostDto.getProblem() );
        question.setExpecting( questionPostDto.getExpecting() );
        question.setMember( questionPostDto.getMember() );

        return question;
    }

    @Override
    public Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto) {
        if ( questionPatchDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setQuestionId( questionPatchDto.getQuestionId() );
        question.setTitle( questionPatchDto.getTitle() );
        question.setProblem( questionPatchDto.getProblem() );
        question.setExpecting( questionPatchDto.getExpecting() );

        return question;
    }

    @Override
    public List<QuestionResponseDto> questionListToResponseDtoList(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<QuestionResponseDto> list = new ArrayList<QuestionResponseDto>( questions.size() );
        for ( Question question : questions ) {
            list.add( questionToQuestionResponseDto( question ) );
        }

        return list;
    }

    @Override
    public List<QuestionResponseAllDto> questionListToResponseAllDtoList(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<QuestionResponseAllDto> list = new ArrayList<QuestionResponseAllDto>( questions.size() );
        for ( Question question : questions ) {
            list.add( questionToQuestionResponseAllDto( question ) );
        }

        return list;
    }
}
