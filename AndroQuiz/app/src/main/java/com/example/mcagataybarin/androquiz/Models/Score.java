package com.example.mcagataybarin.androquiz.Models;

/**
 * Created by mcagataybarin on 3/26/17.
 */

public class Score {
    private int score = 0;

    public Score() {
    }

    public void incrementScore(){
        this.score += 100;
    }

    public int getScore() {
        return score;
    }
}
