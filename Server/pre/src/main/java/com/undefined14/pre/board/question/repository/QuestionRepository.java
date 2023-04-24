package com.undefined14.pre.board.question.repository;

import com.undefined14.pre.board.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByQuestionStatus(Question.QuestionStatus questionStatus, Pageable pageable);
}
