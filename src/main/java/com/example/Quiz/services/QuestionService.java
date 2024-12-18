package com.example.Quiz.services;

import com.example.Quiz.DTO.quizResult;
import com.example.Quiz.model.Question;
import com.example.Quiz.model.QuestionSessions;
import com.example.Quiz.model.User;
import com.example.Quiz.repository.QuestionRepo;
import com.example.Quiz.repository.QuestionSeesionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepository;

    @Autowired
    private QuestionSeesionRepo quizSessionRepository;

    public List<Question> getRandomQuestions(String category, String difficulty, int limit) {
        List<Question> questions = questionRepository.findByCategoryIgnoreCaseAndDifficultyIgnoreCase(category, difficulty);
        System.out.println("Fetched questions: " + questions);
        List<Question> selectedQuestions = questions.stream().limit(limit).collect(Collectors.toList());
        Collections.shuffle(selectedQuestions);
        return selectedQuestions;
    }


    public quizResult evaluateAnswers(Map<Long, Character> answers) {
        int correct = 0;
        int incorrect = 0;

        for (Map.Entry<Long, Character> entry : answers.entrySet()) {
            Long questionId = entry.getKey();
            Character submittedAnswer = entry.getValue();

            Question question = questionRepository.findById(questionId).orElse(null);
            if (question != null) {
                if (question.getCorrectOption()==(submittedAnswer)) {
                    correct++;
                } else {
                    incorrect++;
                }
            }
        }

        return new quizResult(correct, incorrect);
    }

    public QuestionSessions saveQuizSession(Long userId, int totalQuestions, int correctAnswers, int incorrectAnswers, long min, long sec,String category) {
        QuestionSessions session = new QuestionSessions();
        session.setUserId(userId);
        session.setTotalQuestions(totalQuestions);
        session.setCorrectAnswers(correctAnswers);
        session.setIncorrectAnswers(incorrectAnswers);
        session.setQuizDate(LocalDateTime.now());
        session.setTimeTaken_min(min);
        session.setTimeTaken_sec(sec);
        session.setCategory(category);
        return quizSessionRepository.save(session);
    }


}
