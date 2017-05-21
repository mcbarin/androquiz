package com.example.mcagataybarin.androquiz;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.mcagataybarin.androquiz.Fragments.CategoryFragment;
import com.example.mcagataybarin.androquiz.Fragments.ListFragment;
import com.example.mcagataybarin.androquiz.Fragments.QuestionFragment;


/**
 * Created by aea on 25/03/17.
 */

public class QuizActivity2 extends AppCompatActivity implements ListFragment.WorkoutListListener, MemoGameFragment.OnFragmentInteractionListener, EndGameFragment.OnFragmentInteractionListener{
    public static CategoryFragment details;

    public int categoryNumber, questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // For fullscreen activity.

        QuestionFragment cf = new QuestionFragment();

        Intent intent = getIntent();
        cf.categoryNumber= intent.getIntExtra("category", 0);
        cf.questionNumber= intent.getIntExtra("question", 0);

        categoryNumber= intent.getIntExtra("category", 0);
        questionNumber= intent.getIntExtra("question", 0);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, cf);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
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

    public void restartGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void itemClicked(long id) {

        View fragmentContainer = findViewById(R.id.fragment_container);

        if (fragmentContainer != null) {
            if(((int) id) == 0) {
                QuestionData.getInstance().initialize(); // initialize game.
                details = new CategoryFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                ft.replace(R.id.fragment_container, details);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            } else{
                MemoData.getInstance().initialize();
                MemoGameFragment fragment = MemoGameFragment.newInstance(1); // Start from level 1;
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        } else {
            if(((int) id) == 0) {
                QuestionData.getInstance().initialize();
                Intent intent = new Intent(this, CategoryActivity.class);
                startActivity(intent);
            } else {
                MemoData.getInstance().initialize();
                Intent intent = new Intent(this, MemoGameActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
