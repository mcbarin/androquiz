package com.example.mcagataybarin.androquiz;

import android.util.Log;

import com.example.mcagataybarin.androquiz.Models.Category;
import com.example.mcagataybarin.androquiz.Models.Question;
import com.example.mcagataybarin.androquiz.Models.User;

import java.util.ArrayList;

/**
 * Created by mcagataybarin on 2/26/17.
 */


/*
* This class holds the question data and also the state of the game. Whenever new game is started,
* initialize method will be called so that it will start from initial point.
* This class makes use of Category, Question and User classes.
* */
public class QuestionData {
    private Category[] categories = new Category[3];
    private User currentUser;
    private int point = 0;
    private int numAnsweredQuestions = 0;
    private String[] categoryName = {"Sport", "History", "Art"};

    public void initialize(){


        for (int i=0; i<categoryName.length; i++) {
            final int index = i;
            FirebaseFunctions.getInstance().getCategoryQuestions(new Runnable() {
                @Override
                public void run() {
                    Category cat = new Category(categoryName[index], FirebaseFunctions.getInstance().temp_questions);
                    categories[0] = cat;
                }
            }, i);
        }

        this.point = 0;
        this.numAnsweredQuestions = 0;

    }


    public static final QuestionData data = new QuestionData();

    public static QuestionData getInstance(){
        return data;
    }

    public Category[] getCategories(){
        return this.categories;
    }

    public void setUser(User usr){
        this.currentUser = usr;
    }

    public User getUser(){
        return this.currentUser;
    }

    public int getPoint(){
        return this.point;
    }

    public void incrementPoint(int point){
        this.point += point;
    }

    public void incrementNumAnsweredQuestions(){
        this.numAnsweredQuestions += 1;
    }

    public int getNumAnsweredQuestions(){
        return this.numAnsweredQuestions;
    }
}
