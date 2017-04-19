package com.example.lengochoa.toeic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private String[] answerSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        answerSheet = new String[100];
        for(int i =0;i<100;i++){
            answerSheet[i] = "B";
        }

        final Button btnPart1 = (Button)findViewById(R.id.btnPart1);
        btnPart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AnswerPart1 = new Intent(Menu.this,AnswerPart1.class);
                AnswerPart1.putExtra("answerSheet",answerSheet);
                startActivity(AnswerPart1);
            }
        });
        final Button btnPart2 = (Button)findViewById(R.id.btnPart2);
        btnPart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final Button btnPart3 = (Button)findViewById(R.id.btnPart3);
        btnPart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final Button btnPart4 = (Button)findViewById(R.id.btnPart4);
        btnPart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
