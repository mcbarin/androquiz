package com.example.mcagataybarin.androquiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Button[] questionButtons = new Button[15];
    int[] statusColors = {Color.RED, Color.GREEN, Color.BLUE, Color.TRANSPARENT};
    int lastCategory, lastQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // For fullscreen activity.
        setContentView(R.layout.activity_category);

        Button c1q1 = (Button) findViewById(R.id.c1q1);
        c1q1.setOnClickListener(this);
        Button c1q2 = (Button) findViewById(R.id.c1q2);
        c1q2.setOnClickListener(this);
        Button c1q3 = (Button) findViewById(R.id.c1q3);
        c1q3.setOnClickListener(this);
        Button c1q4 = (Button) findViewById(R.id.c1q4);
        c1q4.setOnClickListener(this);
        Button c1q5 = (Button) findViewById(R.id.c1q5);
        c1q5.setOnClickListener(this);
        Button c2q1 = (Button) findViewById(R.id.c2q1);
        c2q1.setOnClickListener(this);
        Button c2q2 = (Button) findViewById(R.id.c2q2);
        c2q2.setOnClickListener(this);
        Button c2q3 = (Button) findViewById(R.id.c2q3);
        c2q3.setOnClickListener(this);
        Button c2q4 = (Button) findViewById(R.id.c2q4);
        c2q4.setOnClickListener(this);
        Button c2q5 = (Button) findViewById(R.id.c2q5);
        c2q5.setOnClickListener(this);
        Button c3q1 = (Button) findViewById(R.id.c3q1);
        c3q1.setOnClickListener(this);
        Button c3q2 = (Button) findViewById(R.id.c3q2);
        c3q2.setOnClickListener(this);
        Button c3q3 = (Button) findViewById(R.id.c3q3);
        c3q3.setOnClickListener(this);
        Button c3q4 = (Button) findViewById(R.id.c3q4);
        c3q4.setOnClickListener(this);
        Button c3q5 = (Button) findViewById(R.id.c3q5);
        c3q5.setOnClickListener(this);

        questionButtons[0] = c1q1;questionButtons[1] = c1q2;questionButtons[2] = c1q3;
        questionButtons[3] = c1q4;questionButtons[4] = c1q5;questionButtons[5] = c2q1;
        questionButtons[6] = c2q2;questionButtons[7] = c2q3;questionButtons[8] = c2q4;
        questionButtons[9] = c2q5;questionButtons[10] = c3q1;questionButtons[11] = c3q2;
        questionButtons[12] = c3q3;questionButtons[13] = c3q4;questionButtons[14] = c3q5;

        updateInfo();
    }


    /*
    * This method is an Override onClick function for question buttons. Whenever a question is clicked,
    * this method will call an it will create an intent to the QuestionActivity.
    */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, QuestionActivity.class);
        switch (v.getId()){
            case R.id.c1q1:
                intent.putExtra("category", 0);
                intent.putExtra("question", 0);
                lastCategory = 0;
                lastQuestion = 0;
                break;
            case R.id.c1q2:
                intent.putExtra("category", 0);
                intent.putExtra("question", 1);
                lastCategory = 0;
                lastQuestion = 1;
                break;
            case R.id.c1q3:
                intent.putExtra("category", 0);
                intent.putExtra("question", 2);
                lastCategory = 0;
                lastQuestion = 2;
                break;
            case R.id.c1q4:
                intent.putExtra("category", 0);
                intent.putExtra("question", 3);
                lastCategory = 0;
                lastQuestion = 3;
                break;
            case R.id.c1q5:
                intent.putExtra("category", 0);
                intent.putExtra("question", 4);
                lastCategory = 0;
                lastQuestion = 4;
                break;
            case R.id.c2q1:
                intent.putExtra("category", 1);
                intent.putExtra("question", 0);
                lastCategory = 1;
                lastQuestion = 0;
                break;
            case R.id.c2q2:
                intent.putExtra("category", 1);
                intent.putExtra("question", 1);
                lastCategory = 1;
                lastQuestion = 1;
                break;
            case R.id.c2q3:
                intent.putExtra("category", 1);
                intent.putExtra("question", 2);
                lastCategory = 1;
                lastQuestion = 2;
                break;
            case R.id.c2q4:
                intent.putExtra("category", 1);
                intent.putExtra("question", 3);
                lastCategory = 1;
                lastQuestion = 3;
                break;
            case R.id.c2q5:
                intent.putExtra("category", 1);
                intent.putExtra("question", 4);
                lastCategory = 1;
                lastQuestion = 4;
                break;
            case R.id.c3q1:
                intent.putExtra("category", 2);
                intent.putExtra("question", 0);
                lastCategory = 2;
                lastQuestion = 0;
                break;
            case R.id.c3q2:
                intent.putExtra("category", 2);
                intent.putExtra("question", 1);
                lastCategory = 2;
                lastQuestion = 1;
                break;
            case R.id.c3q3:
                intent.putExtra("category", 2);
                intent.putExtra("question", 2);
                lastCategory = 2;
                lastQuestion = 2;
                break;
            case R.id.c3q4:
                intent.putExtra("category", 2);
                intent.putExtra("question", 3);
                lastCategory = 2;
                lastQuestion = 3;
                break;
            case R.id.c3q5:
                intent.putExtra("category", 2);
                intent.putExtra("question", 4);
                lastCategory = 2;
                lastQuestion = 4;
                break;
            default:
                break;
        }
        v.setEnabled(false); // Make the button unclickable
        QuestionData.getInstance().incrementNumAnsweredQuestions(); // increment the number if questions.
        startActivityForResult(intent, 1);
    }

    /*
    * This method is initiated when restart button is clicked.
    * So, it will start an activity using intent to the MainActivity.
    */
    public void restartGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*
    * This method is called when there is an intent coming from QuestionActivity.
    * It will take the result of the question, and update the necessary parts of the CategoryActivity.
    */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                int result = data.getIntExtra("result", 0);
                int questionNumber = data.getIntExtra("question", 0);
                int categoryNumber = data.getIntExtra("category", 0);

                // These 2 lines is for setting the background color of button's background drawable object.
                ColorStateList csl = new ColorStateList(new int[][]{{}}, new int[]{statusColors[result]});
                questionButtons[categoryNumber*5 + questionNumber].setBackgroundTintList(csl);

                updateInfo();

                // if all questions are answered, show the end game message.
                if(QuestionData.getInstance().getNumAnsweredQuestions() == 15){
                    TextView finishText = (TextView) findViewById(R.id.finishText);
                    finishText.setText("Game finished. Your Point: " + ""+QuestionData.getInstance().getPoint());
                }
            }
        }
    }

    /*
    * This method updates the current user info and point of the user on Category Activity.
    */
    public void updateInfo(){
        TextView gameInfo = (TextView) findViewById(R.id.gameInfo);
        String username = QuestionData.getInstance().getUser().username;
        int point = QuestionData.getInstance().getPoint();
        gameInfo.setText(String.format("User: %s  Point: %d", username, point));
    }

}
