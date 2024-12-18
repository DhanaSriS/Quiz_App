package com.example.Quiz.repository;

import com.example.Quiz.DTO.Leaderboard;
import com.example.Quiz.model.QuestionSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionSeesionRepo  extends JpaRepository<QuestionSessions, Long> {
    List<QuestionSessions> findByUserId(Long userId);
    long countByUserId(Long userId);

    @Query("SELECT q.category FROM QuestionSessions q WHERE q.userId = :userId GROUP BY q.category ORDER BY COUNT(q) DESC")
    Optional<String> findPreferredCategoryByUserId(@Param("userId") Long userId);

    @Query("SELECT q.userId, u.username, SUM(q.correctAnswers) as totalScore, COUNT(q) as totalAttempts " +
            "FROM QuestionSessions q " +
            "JOIN User u ON q.userId = u.id " +
            "GROUP BY q.userId, u.username " +
            "ORDER BY totalScore DESC, totalAttempts ASC")
    List<Object[]> findLeaderboardData();


}