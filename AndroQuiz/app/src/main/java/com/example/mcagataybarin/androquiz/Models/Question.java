package com.example.mcagataybarin.androquiz.Models;

import com.example.mcagataybarin.androquiz.QuestionData;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by mcagataybarin on 2/26/17.
 */


/*
* This class is created for Question object and holds the necessary information for each question object.
* */
public class Question {
    public String question;
    public ArrayList<String> choices = new ArrayList<>();
    int answer, point, status=3;
    // 0-> false, 1-> true, 2-> no time, 3-> not opened

    public Question(DataSnapshot dataSnapshot){
        this.question = dataSnapshot.child("question").getValue().toString();
        this.point = Integer.parseInt(dataSnapshot.child("point").getValue().toString());
        this.answer = Integer.parseInt(dataSnapshot.child("answer").getValue().toString());
        for (DataSnapshot issue: dataSnapshot.child("choices").getChildren()) {
            this.choices.add(issue.getValue().toString());
        }
    }

        /*
     * When user answers a question we make a call to this function which updates the status of the question
     * and returns the updated status value.
    */

    public int makeChoice(int index){
        if (index == answer) {
            this.status = 1;
            QuestionData.getInstance().incrementPoint(this.point);
        }
        else
            this.status = 0;
        return this.status;
    }

        /*
    *  This method is called when user does not give an answer to the question during the countdown.
    */

    public int timeIsUp(){
        this.status = 2;
        return this.status;
    }

}
