package com.example.mcagataybarin.androquiz;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TempActivity extends AppCompatActivity implements MemoGameFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);



        MemoGameFragment fragment = MemoGameFragment.newInstance(4);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.memo_fragment, fragment).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
