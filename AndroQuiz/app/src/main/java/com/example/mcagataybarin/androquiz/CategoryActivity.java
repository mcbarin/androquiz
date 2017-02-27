package com.example.mcagataybarin.androquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Button c1q1 = (Button) findViewById(R.id.c1q1);
        c1q1.setOnClickListener(this);
        Button c1q2 = (Button) findViewById(R.id.c1q2);
        c1q2.setOnClickListener(this);
        Button c1q3 = (Button) findViewById(R.id.c1q3);
        c1q3.setOnClickListener(this);
        Button c1q4 = (Button) findViewById(R.id.c1q4);
        c1q4.setOnClickListener(this);
        Button c1q5 = (Button) findViewById(R.id.c1q5);
        c1q5.setOnClickListener(this);
        Button c2q1 = (Button) findViewById(R.id.c2q1);
        c2q1.setOnClickListener(this);
        Button c2q2 = (Button) findViewById(R.id.c2q2);
        c2q2.setOnClickListener(this);
        Button c2q3 = (Button) findViewById(R.id.c2q3);
        c2q3.setOnClickListener(this);
        Button c2q4 = (Button) findViewById(R.id.c2q4);
        c2q4.setOnClickListener(this);
        Button c2q5 = (Button) findViewById(R.id.c2q5);
        c2q5.setOnClickListener(this);
        Button c3q1 = (Button) findViewById(R.id.c3q1);
        c3q1.setOnClickListener(this);
        Button c3q2 = (Button) findViewById(R.id.c3q2);
        c3q2.setOnClickListener(this);
        Button c3q3 = (Button) findViewById(R.id.c3q3);
        c3q3.setOnClickListener(this);
        Button c3q4 = (Button) findViewById(R.id.c3q4);
        c3q4.setOnClickListener(this);
        Button c3q5 = (Button) findViewById(R.id.c3q5);
        c3q5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, QuestionActivity.class);
        switch (v.getId()){
            case R.id.c1q1:
                intent.putExtra("category", 0);
                intent.putExtra("question", 0);
                break;
            case R.id.c1q2:
                intent.putExtra("category", 0);
                intent.putExtra("question", 1);
                break;
            case R.id.c1q3:
                intent.putExtra("category", 0);
                intent.putExtra("question", 2);
                break;
            case R.id.c1q4:
                intent.putExtra("category", 0);
                intent.putExtra("question", 3);
                break;
            case R.id.c1q5:
                intent.putExtra("category", 0);
                intent.putExtra("question", 4);
                break;
            case R.id.c2q1:
                intent.putExtra("category", 1);
                intent.putExtra("question", 0);
                break;
            case R.id.c2q2:
                intent.putExtra("category", 1);
                intent.putExtra("question", 1);
                break;
            case R.id.c2q3:
                intent.putExtra("category", 1);
                intent.putExtra("question", 2);
                break;
            case R.id.c2q4:
                intent.putExtra("category", 1);
                intent.putExtra("question", 3);
                break;
            case R.id.c2q5:
                intent.putExtra("category", 1);
                intent.putExtra("question", 4);
                break;
            case R.id.c3q1:
                intent.putExtra("category", 2);
                intent.putExtra("question", 0);
                break;
            case R.id.c3q2:
                intent.putExtra("category", 2);
                intent.putExtra("question", 1);
                break;
            case R.id.c3q3:
                intent.putExtra("category", 2);
                intent.putExtra("question", 2);
                break;
            case R.id.c3q4:
                intent.putExtra("category", 2);
                intent.putExtra("question", 3);
                break;
            case R.id.c3q5:
                intent.putExtra("category", 2);
                intent.putExtra("question", 4);
                break;
            default:
                break;
        }
        startActivity(intent);
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
