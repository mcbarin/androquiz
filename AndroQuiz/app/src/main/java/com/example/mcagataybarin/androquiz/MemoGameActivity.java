package com.example.mcagataybarin.androquiz;

import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MemoGameActivity extends AppCompatActivity implements MemoGameFragment.OnFragmentInteractionListener, EndGameFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        MemoGameFragment fragment = MemoGameFragment.newInstance(1); // Start from level 1;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.memo_fragment, fragment).commit();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //fragment2.isLarge = true;
                if(FirebaseFunctions.getInstance().rara != null) FirebaseFunctions.getInstance().rara.setVisibility(View.GONE);

            }
        }, 6500);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
