package com.example.Quiz.repository;

import com.example.Quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findByCategoryIgnoreCaseAndDifficultyIgnoreCase(String category, String difficulty);
}
