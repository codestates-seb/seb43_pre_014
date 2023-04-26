package com.undefined14.pre.board.anwser.service;

import com.undefined14.pre.auth.jwt.JwtTokenizer;
import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.anwser.repository.AnswerRepository;
import com.undefined14.pre.exception.BusinessLogicException;
import com.undefined14.pre.exception.ExceptionCode;
import com.undefined14.pre.member.entity.Member;
import com.undefined14.pre.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AnswerService {
    private AnswerRepository repository;
    private MemberService memberService;
    private final JwtTokenizer jwtTokenizer;

    // 답변 등록
    public Answer createAnswer(Answer answer, String token) {
        // 헤더로 받아온 Authorization 으로 member 를 찾아서 삽입
        Member member = memberService.findMember(jwtTokenizer.getMemberId(token));
        answer.setMember(member);

        return repository.save(answer);
    }

    // 답변 수정
    public Answer updateAnswer(Answer answer, String token) {

        Member member = memberService.findMember(jwtTokenizer.getMemberId(token));

        answer.setMember(member);

        Answer findAnswer = findVerfiedAnswer(answer.getAnswerId());

        verifiedRequest(answer.getMember().getMemberId(),findAnswer.getMember().getMemberId());

        Optional.ofNullable(answer.getBody())
                .ifPresent(findAnswer::setBody);

        return repository.save(findAnswer);
    }

    // 있는지 조회
    public Answer findAnswer(long answerId) {
        Answer findAnswer = findVerfiedAnswer(answerId);

        if (findAnswer.getAnswerStatus().equals(Answer.AnswerStatus.ANSWER_DELETED)) {
            throw new BusinessLogicException(ExceptionCode.ANSWER_DELETED);
        }

        return findAnswer;
    }

    // 답변 삭제
    public void deleteAnswer(long answerId, String token) {

        Member member = memberService.findMember(jwtTokenizer.getMemberId(token));

        Answer findAnswer = findVerfiedAnswer(answerId);

        verifiedRequest(member.getMemberId(),findAnswer.getMember().getMemberId());

        findAnswer.setAnswerStatus(Answer.AnswerStatus.ANSWER_DELETED);

        repository.save(findAnswer);
    }

    // DB 에서 답변 가져옴
    private Answer findVerfiedAnswer(long answerId) {
        Optional<Answer> optionalAnswer =
                repository.findById(answerId);
        return optionalAnswer.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
    }

    // 작성한 회원과 요청한 회원의 id가 같은지 검증 (수정과 삭제 시 사용해야함)
    private void verifiedRequest(long memberId, long answerMemberId) {
        if (memberId != answerMemberId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_MISMATCHED);
        }
    }
}
