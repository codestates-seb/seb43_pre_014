package com.undefined14.pre.board.question.service;

import com.undefined14.pre.board.question.dto.QuestionResponseDto;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.board.question.mapper.QuestionMapper;
import com.undefined14.pre.board.question.repository.QuestionRepository;
import com.undefined14.pre.exception.BusinessLogicException;
import com.undefined14.pre.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionService {
    private final QuestionRepository repository;
    private final QuestionMapper mapper;

    @Autowired
    public QuestionService(QuestionRepository repository, QuestionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Question> findAll() {
        return repository.findAll();
    }

    public Page<QuestionResponseDto> findQuestions(Pageable pageable) {
        Page<Question> questionPage = repository.findByQuestionStatus(Question.QuestionStatus.QUESTION_ACTIVE, pageable);
        return questionPage.map(mapper::questionToQuestionResponseDto);
    }

    public Question findQuestionById(Long questionId) {
       return findVerifiedQuestion(questionId);
    }

    public Question save(Question question) {
        return repository.save(question);
    }

    public Question updateQuestion(Question question) {
        Question existingQuestion = findVerifiedQuestion(question.getQuestionId());

        existingQuestion.setTitle(question.getTitle());
        existingQuestion.setBody(question.getBody());
        return repository.save(existingQuestion);
    }

    public void deleteQuestionById(Long id) {
        Question question = findQuestionById(id);
        question.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETED);
        repository.save(question);
    }

    private Question findVerifiedQuestion(Long questionId) {

        Optional<Question> optionalQuestion = repository.findById(questionId);
        if (optionalQuestion.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND);
        }

        Question question = optionalQuestion.get();
        if (question.getQuestionStatus() == Question.QuestionStatus.QUESTION_DELETED) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_DELETED);
        }

        return question;
    }
}