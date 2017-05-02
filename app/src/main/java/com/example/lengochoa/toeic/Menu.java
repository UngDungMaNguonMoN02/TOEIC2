package com.example.lengochoa.toeic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private String[] answerSheet;
    private String[] ansKey;
    private String[] audios;
    private int indexTest;
    private ArrayList<Question> questions;
    private EditText txtPoint;
    private int point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

            final Bundle bundle = getIntent().getExtras();
            questions = (ArrayList<Question>) bundle.getSerializable("questions");

            Intent intent = getIntent();
            answerSheet = intent.getStringArrayExtra("answerSheet");
            audios = intent.getStringArrayExtra("audios");
            indexTest = intent.getIntExtra("indexTest", 1);
            ansKey = new String[100];
            int i = 0;
            for (Question q : questions) {
                ansKey[i] = q.getKey();
                i++;
            }
            point = 0;
            for (int j = 0; j < 100; j++) {
                if (answerSheet[j].equals(ansKey[j])) point++;
            }


            txtPoint = (EditText) findViewById(R.id.txtPoint);
            txtPoint.setText("Bạn đúng " + point + " câu/100");


        try {
            final Button btnAnswerPart1 = (Button) findViewById(R.id.btnPart1);
            btnAnswerPart1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent AnswerPart1 = new Intent(Menu.this, AnswerPart1.class);
                    AnswerPart1.putExtra("answerSheet", answerSheet);
                    AnswerPart1.putExtra("ansKey", ansKey);
                    AnswerPart1.putExtra("audios", audios);
                    AnswerPart1.putExtra("indexTest", indexTest);
                    bundle.putSerializable("questions", questions);
                    AnswerPart1.putExtras(bundle);
                    startActivity(AnswerPart1);
                }
            });

        }catch (Exception e){
            e.getMessage();
            Toast.makeText(Menu.this,e.getMessage(), Toast.LENGTH_LONG).show();
        }
            final Button btnPart2 = (Button) findViewById(R.id.btnPart2);
            btnPart2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent AnswerPart2 = new Intent(Menu.this, AnswerPart2.class);
                    AnswerPart2.putExtra("answerSheet", answerSheet);
                    AnswerPart2.putExtra("ansKey", ansKey);
                    AnswerPart2.putExtra("audios", audios);
                    AnswerPart2.putExtra("indexTest", indexTest);
                    bundle.putSerializable("questions", questions);
                    AnswerPart2.putExtras(bundle);
                    startActivity(AnswerPart2);
                }
            });
            final Button btnPart3 = (Button) findViewById(R.id.btnPart3);
            btnPart3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            final Button btnPart4 = (Button) findViewById(R.id.btnPart4);
            btnPart4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

    }
}
