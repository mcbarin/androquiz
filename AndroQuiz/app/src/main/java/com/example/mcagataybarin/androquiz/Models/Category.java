package com.example.mcagataybarin.androquiz.Models;

import java.util.ArrayList;

/**
 * Created by mcagataybarin on 2/26/17.
 */

/*
* This class is created for Category object and holds the questions and name of the category.*/
public class Category {
    String name;
    public ArrayList<Question> questions;

    public Category(String name, ArrayList<Question> questions){
        this.name = name;
        this.questions = questions;
    }


}
