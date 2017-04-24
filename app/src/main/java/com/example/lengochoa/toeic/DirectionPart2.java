package com.example.lengochoa.toeic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DirectionPart2 extends AppCompatActivity {

    private long remainingTime;
    private TextView txtTimer;
    private MediaPlayer mp3;
    private String[] answerSheet;
    private String[] audios;
    private int indexTest;
    private ArrayList<Question> questions;

    private String ConvertTime(int s){
        int m = s/60;
        int sec = s%60;
        return " " + (m>9?m:"0"+m) + " : " + (sec>9?sec:"0"+sec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_part2);

        txtTimer = (TextView)findViewById(R.id.txtTimer);

        Intent intent = getIntent();
        answerSheet = intent.getStringArrayExtra("answerSheet");
        remainingTime = intent.getLongExtra("remainingTime",1L);
        audios = intent.getStringArrayExtra("audios");
        indexTest = intent.getIntExtra("indexTest",1);
        Bundle bundle = getIntent().getExtras();
        questions = (ArrayList<Question>) bundle.getSerializable("questions");

        CounterForTest timer = new CounterForTest(remainingTime,1000);
        timer.start();

        NextPage nextPage = new NextPage(67000,1000);
        nextPage.start();
        mp3 = MediaPlayer.create(this, R.raw.directionp2);
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
            Intent part2 = new Intent(DirectionPart2.this,Part2.class);
            part2.putExtra("remainingTime",remainingTime);
            part2.putExtra("answerSheet",answerSheet);
            part2.putExtra("audios",audios);
            part2.putExtra("indexTest",indexTest);
            Bundle bundle = new Bundle();
            bundle.putSerializable("questions",questions);
            part2.putExtras(bundle);
            startActivity(part2);
        }
    }
}
