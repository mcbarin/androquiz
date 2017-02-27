package com.example.mcagataybarin.androquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        int asd=0;
    }

    public void updateCategories(){
        Category[] categories = QuestionData.getInstance().getCategories();

    }

    // questiondan gelicek şey
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
            }
        }
    }

    public void onClickc1q5(View view){
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("qu", "S,5");
        Button but = (Button) view;
        but.setBackgroundColor(Color.BLACK);
        startActivityForResult(intent, 1);

    }
}
