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
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    public CommentService(CommentRepository commentRepository,
                          QuestionService questionService,
                          AnswerService answerService,
                          MemberService memberService){
        this.commentRepository = commentRepository;
        this.questionService = questionService;
        this.answerService = answerService;
        this.memberService = memberService;
    }

    public Comment createCommentToQuestion(Comment comment, long questId, long tokenId){
        Member member = memberService.findMember(tokenId);
        Question question = questionService.findQuestion(questId);

        comment.setInheritComment(true);
        comment.setQuestion(question);
        comment.setAnswer(null);
        comment.setMemberId(member);

        return commentRepository.save(comment);
    }

    public Comment createCommentToAnswer(Comment comment, long answerId, long tokenId){
        Member member = memberService.findMember(tokenId);
        Answer answer = answerService.findAnswer(answerId);

        comment.setInheritComment(false);
        comment.setQuestion(null);
        comment.setAnswer(answer);
        comment.setMemberId(member);

        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment,long tokenId){
        Comment findComment = findVerifiedCommentByQuery(comment.getCommentId());
        Member findMember = findComment.getMemberId();
        if(findMember.getMemberId() != tokenId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_FORBIDDEN);
        }
        Optional.ofNullable(comment.getContent())
                .ifPresent(content->findComment.setContent(content));
        return commentRepository.save(findComment);
    }

    public void deleteComment(long commentId,long tokenId){
        Comment findComment = findComment(commentId);
        Member findMember = findComment.getMemberId();
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

    public List<Comment> findCommentByQuestionId(long questId){
        Question question = questionService.findQuestion(questId);
        return commentRepository.findByQuest(questId);
    }

    public List<Comment> findCommentByAnswerId(long answerId){
        Answer answer = answerService.findAnswer(answerId);
        return commentRepository.findByAnswer(answerId);
    }
}
