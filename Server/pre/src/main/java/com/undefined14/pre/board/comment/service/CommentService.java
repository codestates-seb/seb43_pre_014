package com.undefined14.pre.board.comment.service;

import com.undefined14.pre.board.anwser.entity.Answer;
import com.undefined14.pre.board.anwser.service.AnswerService;
import com.undefined14.pre.board.question.entity.Question;
import com.undefined14.pre.board.question.service.QuestionService;
import com.undefined14.pre.exception.ExceptionCode;
import com.undefined14.pre.exception.BusinessLogicException;
import com.undefined14.pre.member.entity.Member;
import com.undefined14.pre.member.service.MemberService;
import com.undefined14.pre.board.comment.entity.Comment;
import com.undefined14.pre.board.comment.repository.CommentRepository;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    public Comment createCommentToQuestion(Comment comment, long questId, long tokenId){
        Member member = memberService.findMember(tokenId);
        Question question = questionService.findQuestionById(questId);

        comment.setInheritQuestion(true);
        comment.setQuestion(question);
        comment.setAnswer(null);
        comment.setWriter(member);

        return commentRepository.save(comment);
    }

    public Comment createCommentToAnswer(Comment comment, long answerId, long tokenId){
        Member member = memberService.findMember(tokenId);
        Answer answer = answerService.findAnswer(answerId);

        comment.setInheritQuestion(false);
        comment.setQuestion(null);
        comment.setAnswer(answer);
        comment.setWriter(member);

        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment,long tokenId){
        Comment findComment = findVerifiedCommentByQuery(comment.getCommentId());
        Member findMember = findComment.getWriter();
        if(findMember.getMemberId() != tokenId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_FORBIDDEN);
        }
        Optional.ofNullable(comment.getBody())
                .ifPresent(content->findComment.setBody(content));
        return commentRepository.save(findComment);
    }

    public void deleteComment(long commentId,long tokenId){
        Comment findComment = findComment(commentId);
        Member findMember = findComment.getWriter();
        if(findMember.getMemberId() != tokenId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_FORBIDDEN);
        }
        commentRepository.delete(findComment);
    }

    public Comment findComment(long commentId){
        return findVerifiedCommentByQuery(commentId);
    }

    private Comment findVerifiedCommentByQuery(long commentId){
        Optional<Comment> optionalComment = commentRepository.findByComment(commentId);
        Comment findComment = optionalComment.orElseThrow(()->new BusinessLogicException(
                ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }

    // 조회 시 사용
//    public List<Comment> findCommentByQuestionId(long questId){
//        Question question = questionService.findQuestionById(questId);
//        return commentRepository.findByQuestion(questId);
//    }
//
//    public List<Comment> findCommentByAnswerId(long answerId){
//        Answer answer = answerService.findAnswer(answerId);
//        return commentRepository.findByAnswer(answerId);
//    }
}
