package com.example.lengochoa.toeic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;

public class DirectionPart1 extends AppCompatActivity {

    private long remainingTime = 2700000;
    private int indexTest;
    private TextView txtTimer;
    private MediaPlayer mp3;
    private ArrayList<Question> questions;

    private String ConvertTime(int s){
        int m = s/60;
        int sec = s%60;
        return " " + (m>9?m:"0"+m) + " : " + (sec>9?sec:"0"+sec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_part1);

        txtTimer = (TextView)findViewById(R.id.txtTimer);

        Intent intent = getIntent();
        indexTest = intent.getIntExtra("indexTest",1);
        Bundle bundle = getIntent().getExtras();
        questions = (ArrayList<Question>)bundle.getSerializable("questions");

        CounterForTest counter = new CounterForTest(remainingTime,1000);
        counter.start();

        NextPage timer = new NextPage(92000,1000);
        timer.start();

        mp3 = MediaPlayer.create(this,R.raw.directionp1);
        mp3.start();

    }

    public class CounterForTest extends CountDownTimer {

        CounterForTest(long millisInFuture, long countDownInterval){
            super(millisInFuture,countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            remainingTime = millisUntilFinished;
            txtTimer.setText(ConvertTime((int)millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {}
    }

    public class NextPage extends CountDownTimer{

        public NextPage(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {}

        @Override
        public void onFinish() {
            mp3.release();
            Intent part1 = new Intent(DirectionPart1.this,Part1.class);
            part1.putExtra("remainingTime",remainingTime);
            part1.putExtra("indexTest",indexTest);
            Bundle bundle = new Bundle();
            bundle.putSerializable("questions",questions);
            part1.putExtras(bundle);
            startActivity(part1);
        }
    }
}
