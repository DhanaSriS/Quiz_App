package com.example.Quiz.services;

import com.example.Quiz.model.QuestionSessions;
import com.example.Quiz.repository.QuestionSeesionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class UserAnalyticsService {
    @Autowired
    private QuestionSeesionRepo sessionRepository;

    /**
     * Get the total number of quizzes completed by the user.
     */
    public long getQuizzesCompletedByUser(Long userId) {
        return sessionRepository.countByUserId(userId);
    }

    /**
     * Get the average time taken by the user to complete quizzes.
     */
    public Duration getAverageTimeTaken(Long userId) {
        List<QuestionSessions> sessions = sessionRepository.findByUserId(userId);
        if (sessions.isEmpty()) return Duration.ZERO;

        long totalSeconds = sessions.stream()
                .mapToLong(session -> session.getTimeTaken_sec())
                .sum();

        return Duration.ofSeconds(totalSeconds / sessions.size());
    }

    /**
     * Calculate the accuracy rate for the user.
     */
    public double getAccuracyRate(Long userId) {
        List<QuestionSessions> sessions = sessionRepository.findByUserId(userId);
        if (sessions.isEmpty()) return 0.0;

        long totalQuestions = sessions.stream().mapToLong(QuestionSessions::getTotalQuestions).sum();
        long correctAnswers = sessions.stream().mapToLong(QuestionSessions::getCorrectAnswers).sum();

        return totalQuestions > 0 ? (double) correctAnswers / totalQuestions * 100 : 0.0;
    }

    /**
     * Get the most preferred category based on completed quizzes.
     */
    public String getPreferredCategory(Long userId) {
        return sessionRepository.findPreferredCategoryByUserId(userId)
                .orElse("No category data available");
    }

}
