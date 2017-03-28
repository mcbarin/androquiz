package com.example.mcagataybarin.androquiz.Fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.mcagataybarin.androquiz.Category;
import com.example.mcagataybarin.androquiz.ListActivity;
import com.example.mcagataybarin.androquiz.Question;
import com.example.mcagataybarin.androquiz.QuestionData;
import com.example.mcagataybarin.androquiz.R;

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

import static android.app.Activity.RESULT_OK;

    /*
    * This class is the main java class of the question activity where the user is prompted with a question
    * and there are 4 choices as described in the PDF.
    */


public class QuestionFragment extends Fragment implements View.OnClickListener {

    Question q;
    public int categoryNumber, questionNumber;
    private Activity mActivity;
    public boolean isLarge;
    private int seconds = 10;
    private boolean running, wasRunning;
    View v;
    private CategoryFragment cfrag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        v = getView();


        Category c = QuestionData.getInstance().getCategories()[categoryNumber];
        q = c.questions[questionNumber]; // Question object is retrieved from QuestionData class.

        // Question is displayed with a TextView
        TextView question = (TextView) v.findViewById(R.id.question);
        question.setText(q.question);

        Button buttonA = (Button) v.findViewById(R.id.buttonA);
        Button buttonB = (Button) v.findViewById(R.id.buttonB);
        Button buttonC = (Button) v.findViewById(R.id.buttonC);
        Button buttonD = (Button) v.findViewById(R.id.buttonD);

        buttonA.setText(q.choices[0]);
        buttonB.setText(q.choices[1]);
        buttonC.setText(q.choices[2]);
        buttonD.setText(q.choices[3]);

        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);

        // These lines ensure that timer works properly when user presses a home button or rotates the screen.
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        running = true;
        runTimer();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // For fullscreen activity.
    }

    /*
    * This is an override onclick function for question choices.
    * Since similar actions will happen after each click, having one click listener for each
    * button is better for readability and performance.
    * This method will make choice on question object and return result of the question to the
    * CategoryActivity so that CategoryActivity can update necessary parts in its layout.
    * */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int status = 3;
        switch (v.getId()) {
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

        // we are giving the result back to the CategoryActivity with the user's selected answer.

        mActivity.setResult(RESULT_OK, intent);
        intent.putExtra("result", status);
        intent.putExtra("category", categoryNumber);
        intent.putExtra("question", questionNumber);
        mActivity.finish();



    }


        /*
    * Timer function makes use of Handler class. Each Handler object is associated with a thread
    * and its message queue, it allows us to run the code with a delay of a second so that we can
    * have the countdown from 10 to 0 in the application with no problems.
    */

    private void runTimer() {
        final TextView timeView = (TextView) v.findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%02d", secs);
                timeView.setText(time);
                if (secs < 4) {
                    timeView.setTextColor(Color.RED);
                }
                if (running) {
                    seconds--;
                }

                if (seconds == -1) {
                    Intent intent = new Intent();
                    int status = q.timeIsUp();
                    intent.putExtra("result", status);
                    intent.putExtra("category", categoryNumber);
                    intent.putExtra("question", questionNumber);
                    mActivity.setResult(RESULT_OK, intent);
                    mActivity.finish();
                }

                handler.postDelayed(this, 1000);
            }
        });
    }


        /*
    * When application gets suspended we store wasRunning variable so that when the user comes back
    * we can pass it to onResume and check if timer was running.
    */

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

        /*
    * Depending on whether timer was running, this function will resume the countdown so if we left the application
    * with 5 seconds on the timer, it will continue to count from 5 down to 0.
    */


    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    /*
    * When back button is pressed, this method will set the question as unanswered and it will start
    * the CategoryActivity again. CategoryActivity itself will set the background of the question
    * by using intent's extra context.*/

}
