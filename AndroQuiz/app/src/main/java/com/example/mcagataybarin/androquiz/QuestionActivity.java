package com.example.mcagataybarin.androquiz;

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

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    Question q;
    int categoryNumber, questionNumber;

    private int seconds = 10;
    private boolean running, wasRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // For fullscreen activity.
        Intent intent = getIntent();

        // Here, we get category and question number from intent extra context.
        // Do what you want to do with Question object.
        categoryNumber = intent.getIntExtra("category", 0);
        questionNumber = intent.getIntExtra("question", 0);
        Category c = QuestionData.getInstance().getCategories()[categoryNumber];
        q = c.questions[questionNumber]; // Question object is retrieved from QuestionData class.

        TextView question = (TextView) findViewById(R.id.question);
        question.setText(q.question);


        Button buttonA = (Button) findViewById(R.id.buttonA);
        Button buttonB = (Button) findViewById(R.id.buttonB);
        Button buttonC = (Button) findViewById(R.id.buttonC);
        Button buttonD = (Button) findViewById(R.id.buttonD);

        buttonA.setText(q.choices[0]);
        buttonB.setText(q.choices[1]);
        buttonC.setText(q.choices[2]);
        buttonD.setText(q.choices[3]);

        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        running = true;
        runTimer();
        
    }

    /*
    * This is an override onclick function for question choices.
    * Since similar actions will happen after each click, having one click listener for each
    * button is better for readability and performance.
    * This method will make choice on question object and return result of the question to the
    * CategoryActivity so that CategoryActivity can update necessary parts in its layout.
    * */
    @Override
    public void onClick(View v){
        Intent intent = new Intent();
        int status=3;
        switch (v.getId()){
            case R.id.buttonA:
                status = q.makeChoice(0);
                break;
            case R.id.buttonB:
                status = q.makeChoice(1);
                break;
            case R.id.buttonC:
                status = q.makeChoice(2);
                break;
            case R.id.buttonD:
                status = q.makeChoice(3);
                break;
            default:
                break;
        }
        setResult(RESULT_OK, intent);
        intent.putExtra("result", status);
        intent.putExtra("category", categoryNumber);
        intent.putExtra("question", questionNumber);
        finish();
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%02d", secs);
                timeView.setText(time);
                if(secs < 4){
                    timeView.setTextColor(Color.RED);
                }
                if (running) {
                    seconds--;
                }

                if(seconds == -1){
                    Intent intent = new Intent();
                    int status = q.timeIsUp();
                    setResult(RESULT_OK, intent);
                    intent.putExtra("result", status);
                    intent.putExtra("category", categoryNumber);
                    intent.putExtra("question", questionNumber);
                    finish();
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    /*
    * When back button is pressed, this method will set the question as unanswered and it will start
    * the CategoryActivity again. CategoryActivity itself will set the background of the question
    * by using intent's extra context.*/
    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        int status = q.timeIsUp();
        setResult(RESULT_OK, intent);
        intent.putExtra("result", status);
        intent.putExtra("category", categoryNumber);
        intent.putExtra("question", questionNumber);
        finish();
    }
}
