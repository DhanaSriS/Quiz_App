package com.example.Quiz.controller;

import com.example.Quiz.DTO.Leaderboard;
import com.example.Quiz.DTO.login;
import com.example.Quiz.DTO.quizResult;
import com.example.Quiz.model.QuestionSessions;
import com.example.Quiz.model.User;
import com.example.Quiz.model.Question;
import com.example.Quiz.repository.UserRepo;
import com.example.Quiz.services.LeaderboardService;
import com.example.Quiz.services.QuestionService;

import com.example.Quiz.services.QuestionSessionService;
import com.example.Quiz.services.UserAnalyticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private QuestionSessionService sessionService;

    @Autowired
    private UserAnalyticsService analyticsService;

    @Autowired
    private LeaderboardService leaderservice;
    private final Map<String, Long> activeSessions = new HashMap<>();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody login loginReq) {
        User user = userRepository.findByUsernameAndPassword(loginReq.getUsername(), loginReq.getPassword());
        activeSessions.put("session", user.getId());
        if (user != null) {
            //activeSessions.put("session", user.getId()); // A simple session key, can be enhanced with tokens.
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
        }
    }

    @GetMapping("/questions/random")
    public ResponseEntity<List<Question>> getQuestions(
            @RequestParam String category, @RequestParam String difficulty, @RequestParam int limit, HttpSession session) {


        Long loggedInUser = activeSessions.get("session");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        session.setAttribute("category",category);
        List<Question> questions = questionService.getRandomQuestions(category, difficulty, limit);
        for (Question q : questions) {
            session.setAttribute("sess" + q.getId(), q.getId());
        }
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/questions/submit")
    public ResponseEntity<String> submitAnswers(@RequestBody List<Character> answers, HttpSession session) {
        // Validate if the user is logged in
        Long loggedInUserId = activeSessions.get("session");
        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first!");
        }

        // Retrieve the quiz start time from the session
        LocalDateTime quizStartTime = (LocalDateTime) session.getAttribute("quizStartTime");
        if (quizStartTime == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quiz has not been started yet!");
        }

        // Calculate total time taken
        LocalDateTime quizEndTime = LocalDateTime.now();
        Duration timeTaken = Duration.between(quizStartTime, quizEndTime);
        long minutesTaken = timeTaken.toMinutes();
        long secondsTaken = timeTaken.toSecondsPart();


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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Number of answers does not match the number of questions displayed!");
        }

        // Map answers to question IDs and evaluate them
        Map<Long, Character> mappedAnswers = new HashMap<>();
        for (int i = 0; i < sessionQuestionIds.size(); i++) {
            mappedAnswers.put(sessionQuestionIds.get(i), answers.get(i));
        }

        // Evaluate answers
        quizResult result = questionService.evaluateAnswers(mappedAnswers);

        // Save the quiz session results
        questionService.saveQuizSession(loggedInUserId, sessionQuestionIds.size(), result.getCorrect(), result.getIncorrect(),minutesTaken,secondsTaken, (String)session.getAttribute("category"));

        // Clear session data for this quiz
        sessionQuestionIds.forEach(qId -> session.removeAttribute("sess" + qId));

        clearSessionData(session);

        return ResponseEntity.ok("Quiz completed! Correct: " + result.getCorrect() +
                ", Incorrect: " + result.getIncorrect());
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<QuestionSessions>> getUserSessions() {
        Long loggedInUserId = activeSessions.get("session");
        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<QuestionSessions> sessions = sessionService.getSessionsByUser(loggedInUserId);
        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/quiz/start")
    public ResponseEntity<String> startQuiz(HttpSession session) {
        Long loggedInUserId = activeSessions.get("session");
        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first!");
        }

        // Record the start time in the session
        session.setAttribute("quizStartTime", LocalDateTime.now());

        return ResponseEntity.ok("Quiz started! Good luck!");
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getUserAnalytics() {
        Long loggedInUserId = activeSessions.get("session");
        if (loggedInUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("quizzesCompleted", analyticsService.getQuizzesCompletedByUser(loggedInUserId));
        analytics.put("averageTimeTaken", analyticsService.getAverageTimeTaken(loggedInUserId).toMinutes() + " mins");
        analytics.put("accuracyRate", analyticsService.getAccuracyRate(loggedInUserId) + "%");
        analytics.put("preferredCategory", analyticsService.getPreferredCategory(loggedInUserId));

        return ResponseEntity.ok(analytics);
    }

    private void clearSessionData(HttpSession session) {
        session.getAttributeNames().asIterator().forEachRemaining(attr -> {
            if (attr.equals("quizStartTime")) {
                session.removeAttribute(attr);
            }
        });
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<Leaderboard>> getLeaderboard() {
        return ResponseEntity.ok(leaderservice.getLeaderboard());
    }

}