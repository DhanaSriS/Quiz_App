package com.example.Quiz.controller;

import com.example.Quiz.DTO.login;
import com.example.Quiz.DTO.quizResult;
import com.example.Quiz.model.QuestionSessions;
import com.example.Quiz.model.User;
import com.example.Quiz.model.Question;
import com.example.Quiz.repository.UserRepo;
import com.example.Quiz.services.QuestionService;

import com.example.Quiz.services.QuestionSessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private QuestionSessionService sessionService;

    private final Map<String, User> activeSessions = new HashMap<>();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody login loginReq) {
        User user = userRepository.findByUsernameAndPassword(loginReq.getUsername(), loginReq.getPassword());
        activeSessions.put("session", user);
        if (user != null) {
            activeSessions.put("session", user); // A simple session key, can be enhanced with tokens.
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
        }
    }
    @PostMapping("/start")
    public ResponseEntity<String> startQuiz() {
        User loggedInUser = activeSessions.get("session");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first!");
        }
        return ResponseEntity.ok("Quiz started for user: " + loggedInUser.getUsername());
    }

    @GetMapping("/questions/random")
    public ResponseEntity<List<Question>> getQuestions(
            @RequestParam String category, @RequestParam String difficulty, @RequestParam int limit, HttpSession session) {
        User loggedInUser = activeSessions.get("session");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<Question> questions = questionService.getRandomQuestions(category, difficulty, limit);
        for(Question q:questions){
            session.setAttribute("sess"+q.getId(), q.getId());
        }
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/questions/submit")
    public ResponseEntity<String> submitAnswers(@RequestBody List<Character> answers, HttpSession session) {
        // Validate if the user is logged in
        User loggedInUser = activeSessions.get("session");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first!");
        }

        // Retrieve all question IDs from the session in the order they were displayed
        List<Long> sessionQuestionIds = new ArrayList<>();
        session.getAttributeNames().asIterator().forEachRemaining(attr -> {
            if (attr.startsWith("sess")) {
                sessionQuestionIds.add((Long) session.getAttribute(attr));
            }
        });

        if (sessionQuestionIds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No active quiz session found!");
        }

        // Validate that the number of answers matches the number of questions displayed
        if (answers.size() != sessionQuestionIds.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Number of answers does not match the number of questions displayed!");
        }

        // Map answers to question IDs and evaluate them
        Map<Long, Character> mappedAnswers = new HashMap<>();
        for (int i = 0; i < sessionQuestionIds.size(); i++) {
            mappedAnswers.put(sessionQuestionIds.get(i), answers.get(i));
        }

        // Evaluate answers
        quizResult result = questionService.evaluateAnswers(mappedAnswers);

        // Optionally save the quiz session (uncomment if needed)
        // questionService.saveQuizSession(loggedInUser, sessionQuestionIds.size(), result.getCorrect(), result.getIncorrect());

        // Clear session data for this qui
        // sessionQuestionIds.forEach(qId -> session.removeAttribute("sess" + qId));

        questionService.saveQuizSession((Long)session.getAttribute("session"),result.getCorrect()+result.getIncorrect(), result.getCorrect(), result.getIncorrect());

        return ResponseEntity.ok("Quiz completed! Correct: " + result.getCorrect() +
                ", Incorrect: " + result.getIncorrect());
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<QuestionSessions>> getUserSessions(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("session");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<QuestionSessions> sessions = sessionService.getSessionsByUser(loggedInUser.getId());
        return ResponseEntity.ok(sessions);
    }
}
