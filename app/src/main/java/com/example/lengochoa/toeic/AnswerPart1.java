package com.example.lengochoa.toeic;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AnswerPart1 extends AppCompatActivity {

    private String[] answerSheet;
    private String[] keys;
    private RadioButton a,b,c,d;
    private Button next,pre;
    private TextView txtnoq;
    private int curans;

    private void InitKey(){
        keys = new String[100];
        for(int i = 0;i<100;i++){
            keys[i] = "A";
        }
    }

    private void SetAns(){
        Question q = new Question();
        String[] answer = q.getAnswer();
        a.setText(answer[0]);
        b.setText(answer[1]);
        c.setText(answer[2]);
        d.setText(answer[3]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_part1);
        InitKey();

        Intent intent = getIntent();
        answerSheet = intent.getStringArrayExtra("answerSheet");

        a = (RadioButton)findViewById(R.id.rdba);
        b = (RadioButton)findViewById(R.id.rdbb);
        c = (RadioButton)findViewById(R.id.rdbc);
        d = (RadioButton)findViewById(R.id.rdbd);

        curans = 0;
        txtnoq = (TextView)findViewById(R.id.txtnoq);
        pre = (Button)findViewById(R.id.btnPre);
        next = (Button)findViewById((R.id.btnNext));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curans<9){
                    curans++;
                    txtnoq.setText(curans+"/10");
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curans>0){
                    curans--;
                    txtnoq.setText(curans+"/10");
                }
            }
        });

        if(keys[curans].equals("A")) a.setTextColor(Color.GREEN);
        else if(keys[curans].equals("B")) b.setTextColor(Color.GREEN);
        else if(keys[curans].equals("C")) c.setTextColor(Color.GREEN);
        else d.setTextColor(Color.GREEN);

        if(!answerSheet[curans].equals(keys[curans])){
            if(answerSheet[curans].equals("A")) {
                a.setChecked(true);
                a.setTextColor(Color.RED);
            }
            else if(answerSheet[curans].equals("B")){
                b.setChecked(true);
                b.setTextColor(Color.RED);
            }
            else if(answerSheet[curans].equals("C")) {
                c.setChecked(true);
                c.setTextColor(Color.RED);
            }
            else {
                d.setChecked(true);
                d.setTextColor(Color.RED);
            }
        }
    }
}
