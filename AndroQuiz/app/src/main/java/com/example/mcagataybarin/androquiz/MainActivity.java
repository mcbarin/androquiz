package com.example.mcagataybarin.androquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


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
    public void usernameEntered(View view) {

        EditText username = (EditText) findViewById(R.id.editText);
        User usr = new User(username.getText().toString());
        QuestionData.getInstance().initialize();
        QuestionData.getInstance().setUser(usr);

        Intent intent = new Intent(this, ListActivity.class);
        MainActivity.this.startActivity(intent);

    }
}
