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
    private int[] grade = {5,5,5,5,5,5,5,10,15,20,25,30,35,45,45,50,55,60,65,70,75,80,85,90,95,100,105,110,115,120,125,135,140,145,150,155,160,165,170,180,185,190,195,200,210,220,225,230,235240,245,250,255,260,270,275,280,285,295,300,305,310,315,320,325,330,335,340,345,350,360,370,375,375,380,390,395,400,405,410,420,425,430,435,440,450,455,460,470,475,480,485,490,495,495,495,495,495,495,495,495};
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
            txtPoint.setText("Số câu đúng: " + point + "/100" + "\n" + "Bạn đạt được " + grade[point] + " điểm");

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
                    Intent AnswerPart3 = new Intent(Menu.this, AnswerPart3.class);
                    AnswerPart3.putExtra("answerSheet", answerSheet);
                    AnswerPart3.putExtra("ansKey", ansKey);
                    AnswerPart3.putExtra("audios", audios);
                    AnswerPart3.putExtra("indexTest", indexTest);
                    bundle.putSerializable("questions", questions);
                    AnswerPart3.putExtras(bundle);
                    startActivity(AnswerPart3);
                }
            });
            final Button btnPart4 = (Button) findViewById(R.id.btnPart4);
            btnPart4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent AnswerPart4 = new Intent(Menu.this, AnswerPart4.class);
                    AnswerPart4.putExtra("answerSheet", answerSheet);
                    AnswerPart4.putExtra("ansKey", ansKey);
                    AnswerPart4.putExtra("audios", audios);
                    AnswerPart4.putExtra("indexTest", indexTest);
                    bundle.putSerializable("questions", questions);
                    AnswerPart4.putExtras(bundle);
                    startActivity(AnswerPart4);
                }
            });

            final Button btnTest = (Button)findViewById(R.id.btnTest);
            btnTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent test = new Intent(Menu.this, MainActivity.class);
                    startActivity(test);
                }
            });

    }
}
