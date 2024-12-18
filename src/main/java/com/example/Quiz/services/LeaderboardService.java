package com.example.Quiz.services;

import com.example.Quiz.DTO.Leaderboard;
import com.example.Quiz.repository.QuestionRepo;
import com.example.Quiz.repository.QuestionSeesionRepo;
import com.example.Quiz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    private QuestionRepo questionRepository;

    @Autowired
    private final QuestionSeesionRepo quizSessionRepository;

    @Autowired
    private final UserRepo userrepository;

    public LeaderboardService(QuestionSeesionRepo questionSessionsRepository, UserRepo userRepository) {
        this.quizSessionRepository = questionSessionsRepository;
        this.userrepository = userRepository;
    }

    public List<Leaderboard> getLeaderboard() {
        return quizSessionRepository
                .findLeaderboardData()
                .stream()
                .map(data -> new Leaderboard(
                        (Long) data[0],
                        (String) data[1],
                        ((Long) data[2]).intValue(), // Total score
                        ((Long) data[3]).intValue()  // Total attempts
                ))
                .collect(Collectors.toList());
    }
}
