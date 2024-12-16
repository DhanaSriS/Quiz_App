package com.example.Quiz.repository;

import com.example.Quiz.model.QuestionSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionSeesionRepo  extends JpaRepository<QuestionSessions, Long> {
    List<QuestionSessions> findByUserId(Long userId);
}