package com.example.mcagataybarin.androquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void level1(View v){
        MemoData.getInstance().initialize();
        FirebaseFunctions.getInstance().memochal = true;
        FirebaseFunctions.getInstance().gamelevel = 1;
        Intent intent = new Intent(this, MemoGameActivity.class);
        startActivity(intent);
    }

    public void level2(View v){
        MemoData.getInstance().initialize();
        FirebaseFunctions.getInstance().memochal = true;
        FirebaseFunctions.getInstance().gamelevel = 2;
        Intent intent = new Intent(this, MemoGameActivity.class);
        startActivity(intent);
    }

    public void level3(View v){
        MemoData.getInstance().initialize();
        FirebaseFunctions.getInstance().memochal = true;
        FirebaseFunctions.getInstance().gamelevel = 3;
        Intent intent = new Intent(this, MemoGameActivity.class);
        startActivity(intent);
    }

}
