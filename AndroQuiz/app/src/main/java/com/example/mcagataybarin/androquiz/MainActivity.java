package com.example.mcagataybarin.androquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mcagataybarin.androquiz.Models.User;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // For fullscreen activity.
        setContentView(R.layout.activity_main);

        // These 3 lines are for setting the font for textview of name of the game.
        TextView name = (TextView) findViewById(R.id.textView2);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "cartoon_font.ttf");
        name.setTypeface(face);
    }

    /*
    * When username entered, it will start the Category Activity.
    * It will also initialize the QuestionData class so that all information will be returned to
    * its initial point.
    */

}
