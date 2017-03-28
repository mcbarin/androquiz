package com.example.mcagataybarin.androquiz;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.mcagataybarin.androquiz.Fragments.CategoryFragment;
import com.example.mcagataybarin.androquiz.Fragments.ListFragment;
import com.example.mcagataybarin.androquiz.Fragments.QuestionFragment;


/**
 * Created by aea on 25/03/17.
 */

public class ListActivity extends AppCompatActivity implements ListFragment.WorkoutListListener, MemoGameFragment.OnFragmentInteractionListener{
    public static CategoryFragment details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
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
                details = new CategoryFragment();
                details.isLarge = true;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, details);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            } else{
                MemoGameFragment fragment = MemoGameFragment.newInstance(1); // Start from level 1;
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, fragment).commit();
            }
        } else {
            if(((int) id) == 0) {
                Intent intent = new Intent(this, CategoryActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}