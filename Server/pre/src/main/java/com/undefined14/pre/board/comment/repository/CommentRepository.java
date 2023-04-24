package com.undefined14.pre.board.comment.repository;

import com.undefined14.pre.board.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c FROM Comment c WHERE c.commentId = :commentId")
    Optional<Comment> findByComment(long commentId);

    @Query(value = "SELECT c FROM Comment c WHERE c.question.questionId = :questionId")
    List<Comment> findByQuestion(long questionId);

    @Query(value = "SELECT c FROM Comment c WHERE c.answer.answerId = :answerId")
    List<Comment> findByAnswer(long answerId);
}