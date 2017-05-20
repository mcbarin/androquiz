package com.example.mcagataybarin.androquiz.Models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by mcagataybarin on 5/8/17.
 */

@IgnoreExtraProperties
public class HighScore {
    public String username;
    public int score;


    public HighScore(){

    }

    public HighScore(String username, int score){
        this.score = score;
        this.username = username;
    }

}
