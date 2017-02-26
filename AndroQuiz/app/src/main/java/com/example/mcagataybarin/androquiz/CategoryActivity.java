package com.example.mcagataybarin.androquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        int asd=0;
    }

    public void updateCategories(){
        Category[] categories = QuestionData.getInstance().getCategories();


    }
}
