package com.undefined14.pre.comment.repository;

import com.undefined14.pre.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByQuestionIdAndParentIdIsNullOrderByCreateAtDesc(Long questionId);

    List<Comment> findByAnswerIdAndParentIdIsNullOrderByCreateAtDesc(Long answerId);

    List<Comment> findByParentIdOrderByCreateAtDesc(Long parentId);

}