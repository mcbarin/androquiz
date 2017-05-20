package com.example.mcagataybarin.androquiz.Models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by mcagataybarin on 5/8/17.
 */

@IgnoreExtraProperties
public class GameState {
    public int gameType; // 0 for quiz, 1 for memory
    public int gameLevel; // for quiz game, category number (from 0 to 2) for memory game, level 1,2 or 3
    public String creator, opponent;

    public int score1, score2; // 1 for creator, 2 for opponent
    public int time1, time2;
    public int flags; // 1,2,3 -- buna göre her levelda bir flag kombinasyonu tutalım onu gösterelim userlara
    public boolean completed;


    public GameState(){

    }

    public GameState(int gameType, int gameLevel, String creator, String opponent, int score1, int score2, int time1, int time2, int flags, boolean completed) {
        this.gameType = gameType;
        this.gameLevel = gameLevel;
        this.creator = creator;
        this.opponent = opponent;
        this.score1 = score1;
        this.score2 = score2;
        this.time1 = time1;
        this.time2 = time2;
        this.flags = flags;
        this.completed = completed;
    }
    public String toString(){
        return this.gameType + " " + this.gameLevel + " " + this.creator + " " + this.opponent + " " + this.score1 + " " + this.score2
                + " " + this.time1 + " " + this.time2 + " " + this.flags;

    }
}
