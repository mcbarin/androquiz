package com.example.mcagataybarin.androquiz;

import android.app.Activity;
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

import com.example.mcagataybarin.androquiz.Fragments.CategoryFragment;

public class CategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // For fullscreen activity.
        setContentView(R.layout.activity_category);


        CategoryFragment cf = (CategoryFragment) getFragmentManager().findFragmentById(R.id.category_frag);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }

    public void restartGame(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}