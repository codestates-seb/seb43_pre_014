package com.undefined14.pre.board.comment.service;

import com.undefined14.pre.auth.jwt.JwtTokenizer;
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

import static com.undefined14.pre.board.comment.entity.Comment.PostType.*;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;
    private final Comment comment;
    private final JwtTokenizer jwtTokenizer;


    public Comment createCommentToQuestion(Comment comment, long questId, String token){
        Member member = memberService.findMember(token);
        Question question = questionService.findQuestionById(questId);

        comment.setPostType(QUESTION);
        comment.setQuestion(question);
        comment.setAnswer(null);
        comment.setMember(member);
        comment.setCommentStatus(Comment.CommentStatus.COMMENT_POSTED);

        return commentRepository.save(comment);
    }

    public Comment createCommentToAnswer(Comment comment, long answerId, String token){
        Member member = memberService.findMember(token);
        Answer answer = answerService.findAnswer(answerId);

        comment.setPostType(ANSWER);
        comment.setQuestion(null);
        comment.setAnswer(answer);
        comment.setMember(member);
        comment.setCommentStatus(Comment.CommentStatus.COMMENT_POSTED);

        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment,String token){
        Comment findComment = findVerifiedCommentByQuery(comment.getCommentId());
        Member findMember = findComment.getMember();
        if(findMember.getMemberId() != jwtTokenizer.getMemberId(token)){
            throw new BusinessLogicException(ExceptionCode.MEMBER_FORBIDDEN);
        }
        Optional.ofNullable(comment.getBody())
                .ifPresent(findComment::setBody);
        return commentRepository.save(findComment);
    }

    public void deleteComment(long commentId,String token){
        Comment findComment = findComment(commentId);
        Member findMember = findComment.getMember();

        if(findMember.getMemberId() != jwtTokenizer.getMemberId(token)){
            throw new BusinessLogicException(ExceptionCode.MEMBER_FORBIDDEN);
        }
        comment.setCommentStatus(Comment.CommentStatus.COMMENT_DELETED);

        commentRepository.delete(findComment);
    }

    public Comment findComment(long commentId){
        return findVerifiedCommentByQuery(commentId);
    }

    private Comment findVerifiedCommentByQuery(long commentId){
        Optional<Comment> optionalComment = commentRepository.findByComment(commentId);
        Comment findComment = optionalComment.orElseThrow(()->new BusinessLogicException(
                ExceptionCode.COMMENT_NOT_FOUND));

        Comment comment = optionalComment.get();
        if (comment.getCommentStatus() == Comment.CommentStatus.COMMENT_DELETED) {
            throw new BusinessLogicException(ExceptionCode.COMMENT_DELETED);
        }
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
