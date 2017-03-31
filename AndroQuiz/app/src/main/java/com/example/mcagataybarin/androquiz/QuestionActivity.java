package com.example.mcagataybarin.androquiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mcagataybarin.androquiz.Fragments.CategoryFragment;
import com.example.mcagataybarin.androquiz.Fragments.QuestionFragment;

    /*
    * This class is the main java class of the question activity where the user is prompted with a question
    * and there are 4 choices as described in the PDF.
    */


public class QuestionActivity extends Activity{

    public int categoryNumber, questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // For fullscreen activity.
        setContentView(R.layout.activity_question);

        QuestionFragment cf = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_frag);

        Intent intent = getIntent();
        cf.categoryNumber= intent.getIntExtra("category", 0);
        cf.questionNumber= intent.getIntExtra("question", 0);

        categoryNumber= intent.getIntExtra("category", 0);
        questionNumber= intent.getIntExtra("question", 0);

    }

    public void onBackPressed() {

        Intent intent = new Intent();
        int status = 2;
        setResult(RESULT_OK, intent);
        intent.putExtra("result", status);
        intent.putExtra("category", categoryNumber);
        intent.putExtra("question", questionNumber);
        finish();

    }

}
