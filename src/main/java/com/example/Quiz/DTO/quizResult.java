package com.example.Quiz.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class quizResult {
    private int correct;
    private int incorrect;

    public quizResult(int correct,int incorrect){
        this.correct=correct;
        this.incorrect=incorrect;
    }
}
