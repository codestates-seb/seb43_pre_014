package com.undefined14.pre.comment.service;

import java.util.List;
import java.util.Optional;

import com.undefined14.pre.comment.dto.CommentDto;
import com.undefined14.pre.comment.entity.Comment;
import com.undefined14.pre.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setCreateAt(commentDto.getCreateAt());
        comment.setStatus(commentDto.getStatus());
        comment.setUserId(commentDto.getUserId());
        comment.setQuestionId(commentDto.getQuestionId());
        comment.setAnswerId(commentDto.getAnswerId());
        comment.setDepth(commentDto.getDepth());
        comment.setParentId(commentDto.getParentId());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByQuestionId(Long questionId) {
        return commentRepository.findByQuestionIdAndParentIdIsNullOrderByCreateAtDesc(questionId);
    }

    public List<Comment> getCommentsByAnswerId(Long answerId) {
        return commentRepository.findByAnswerIdAndParentIdIsNullOrderByCreateAtDesc(answerId);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment updateComment(Long id, CommentDto commentDto) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Comment updatedComment = comment.get();
            updatedComment.setBody(commentDto.getBody());
            updatedComment.setStatus(commentDto.getStatus());
            updatedComment.setUpdateAt(commentDto.getUpdateAt());
            return commentRepository.save(updatedComment);
        } else {
            return null;
        }
    }

    public boolean deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
            return true;
        } else {
            return false;
        }
    }

    public List<Comment> getRepliesByCommentId(Long commentId) {
        return commentRepository.findByParentIdOrderByCreateAtDesc(commentId);
    }

}