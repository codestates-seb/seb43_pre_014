package com.undefined14.pre.board.anwser.service;

import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.anwser.repository.AnswerRepository;
import com.undefined14.pre.exception.BusinessLogicException;
import com.undefined14.pre.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AnswerService {
    private AnswerRepository repository;

    // 답변 등록
    public Answer createAnswer(Answer answer) {
        return repository.save(answer);
    }

    // 답변 수정
    public Answer updateAnswer(Answer answer) {

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
    public void deleteAnswer(Answer answer, long answerId) {
        Answer findAnswer = findVerfiedAnswer(answerId);

        verifiedRequest(answer.getMember().getMemberId(),findAnswer.getMember().getMemberId());

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
