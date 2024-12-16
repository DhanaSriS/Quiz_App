package com.example.Quiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class QuestionSessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private LocalDateTime quizDate;

    // Getters and Setters
}
