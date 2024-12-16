package com.example.Quiz.controller;

import com.example.Quiz.DTO.login;
import com.example.Quiz.model.User;
import com.example.Quiz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

//    @Autowired
//    //private QuestionService quizService;
//
    @Autowired
    private UserRepo userRepository;

    private final Map<String, User> activeSessions = new HashMap<>();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody login loginReq) {
        User user = userRepository.findByUsernameAndPassword(loginReq.getUsername(), loginReq.getPassword());
        if (user != null) {
            activeSessions.put("session", user); // A simple session key, can be enhanced with tokens.
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
        }
    }

}
