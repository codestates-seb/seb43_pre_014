package com.undefined14.pre.board.question.service;

import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.board.question.dto.QuestionResponseAllDto;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.board.question.mapper.QuestionMapper;
import com.undefined14.pre.board.question.repository.QuestionRepository;
import com.undefined14.pre.exception.BusinessLogicException;
import com.undefined14.pre.exception.ExceptionCode;
import com.undefined14.pre.member.entity.Member;
import com.undefined14.pre.member.service.MemberService;
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
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;

    @Autowired
    public QuestionService(QuestionRepository repository, QuestionMapper mapper, JwtTokenizer jwtTokenizer, MemberService memberService) {
        this.repository = repository;
        this.mapper = mapper;
        this.jwtTokenizer = jwtTokenizer;
        this.memberService = memberService;
    }

    public List<Question> findAll() {
        return repository.findAll();
    }

    public Page<QuestionResponseAllDto> findQuestions(Pageable pageable) {
        Page<Question> questionPage = repository.findByQuestionStatus(Question.QuestionStatus.QUESTION_ACTIVE, pageable);
        return questionPage.map(mapper::questionToQuestionResponseAllDto);
    }

    public Question findQuestionById(Long questionId) {
       return findVerifiedQuestion(questionId);
    }

    public Question save(Question question, String token) {
        Member member = memberService.findMember(token);
        question.setMember(member);
        return repository.save(question);
    }

    public Question updateQuestion(Question question, String token) {
        Long memberId = jwtTokenizer.getMemberId(token);
        Question existingQuestion = findVerifiedQuestion(question.getQuestionId());

        verifiedRequest(memberId, existingQuestion.getMember().getMemberId());

        Optional.ofNullable(question.getTitle())
                .ifPresent(existingQuestion::setTitle);
        Optional.ofNullable(question.getProblem())
                .ifPresent(existingQuestion::setProblem);
        Optional.ofNullable(question.getExpecting())
                .ifPresent(existingQuestion::setExpecting);
        Optional.ofNullable(question.getTags())
                .ifPresent(existingQuestion::setTags);
        return repository.save(existingQuestion);
    }

    public void deleteQuestionById(Long id, String token) {
        Long memberId = jwtTokenizer.getMemberId(token);
        Question question = findQuestionById(id);

        verifiedRequest(memberId, question.getMember().getMemberId());
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
    private void verifiedRequest(Long memberId, Long questionMemberId) {
        if (memberId != questionMemberId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_MISMATCHED);
        }
    }
}