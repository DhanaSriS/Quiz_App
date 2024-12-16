package com.example.Quiz.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.time.LocalDateTime;

@Entity
@Table(name="quiz_sessions")
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
