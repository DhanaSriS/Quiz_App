package com.example.Quiz.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leaderboard {

    private Long userId;
    private String username;
    private Integer totalScore;
    private Integer totalAttempts;


}
