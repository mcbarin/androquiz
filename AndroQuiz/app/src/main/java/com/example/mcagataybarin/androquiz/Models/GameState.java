package com.example.mcagataybarin.androquiz.Models;

/**
 * Created by mcagataybarin on 5/8/17.
 */

public class GameState {
    int gameType; // 0 for quiz, 1 for memory
    int gameLevel; // for quiz game, category number (from 0 to 2) for memory game, level 1,2 or 3
    String creatorUsername, opponentUsername;

    int score1, score2; // 1 for creator, 2 for opponent
    int time1, time2;

    public GameState(int gameType, int gameLevel, String creatorUsername, String opponentUsername, int score1, int score2, int time1, int time2) {
        this.gameType = gameType;
        this.gameLevel = gameLevel;
        this.creatorUsername = creatorUsername;
        this.opponentUsername = opponentUsername;
        this.score1 = score1;
        this.score2 = score2;
        this.time1 = time1;
        this.time2 = time2;
    }
}
