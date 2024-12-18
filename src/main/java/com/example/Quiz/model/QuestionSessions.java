package com.example.Quiz.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.time.Duration;
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
    private String category;
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private LocalDateTime quizDate;
    private long timeTaken_min;
    private long timeTaken_sec;
    // Getters and Setters
}
