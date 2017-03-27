package com.example.mcagataybarin.androquiz;

/**
 * Created by mcagataybarin on 2/26/17.
 */


/*
* This class is created for Question object and holds the necessary information for each question object.
* */
public class Question {
    public String question;
    public String[] choices = new String[4];
    int answer, point, status=3;
    // 0-> false, 1-> true, 2-> no time, 3-> not opened

    public Question(String question, String[] choices, int answer, int point){
        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.point = point;
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
