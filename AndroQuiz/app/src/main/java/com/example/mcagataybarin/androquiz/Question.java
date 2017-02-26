package com.example.mcagataybarin.androquiz;

/**
 * Created by mcagataybarin on 2/26/17.
 */

public class Question {
    String question;
    String[] choices = new String[4];
    int answer, point, status=3;
    // 0-> false, 1-> true, 2-> no time, 3-> not opened

    public Question(String question, String[] choices, int answer, int point){
        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.point = point;
    }

    public boolean isCorrect(int index){
        boolean result = index == answer;
        if (result)
            this.status = 1;
        else
            this.status = 0;
        return result;
    }

    public void timeIsUp(){
        this.status = 2;
    }

}
