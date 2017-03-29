package com.example.mcagataybarin.androquiz;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MemoGameActivity extends AppCompatActivity implements MemoGameFragment.OnFragmentInteractionListener, EndGameFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        MemoGameFragment fragment = MemoGameFragment.newInstance(1); // Start from level 1;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.memo_fragment, fragment).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
