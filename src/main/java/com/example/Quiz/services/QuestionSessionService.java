package com.example.Quiz.services;

import com.example.Quiz.model.QuestionSessions;
import com.example.Quiz.repository.QuestionSeesionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionSessionService {

    @Autowired
    private QuestionSeesionRepo sessionRepository;

    public List<QuestionSessions> getSessionsByUser(Long userId) {
        return sessionRepository.findByUserId(userId);
    }
}
