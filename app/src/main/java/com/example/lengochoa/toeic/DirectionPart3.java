package com.example.lengochoa.toeic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DirectionPart3 extends AppCompatActivity {

    private long remainingTime;
    private TextView txtTimer;
    private MediaPlayer mp3;
    private String[] answerSheet;
    private String[] audios;
    private int indexTest;

    private String ConvertTime(int s){
        int m = s/60;
        int sec = s%60;
        return " " + (m>9?m:"0"+m) + " : " + (sec>9?sec:"0"+sec);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_part3);

        txtTimer = (TextView)findViewById(R.id.txtTimer);

        Intent intent = getIntent();
        answerSheet = intent.getStringArrayExtra("answerSheet");
        remainingTime = intent.getLongExtra("remainingTime",1L);
        audios = intent.getStringArrayExtra("audios");
        indexTest = intent.getIntExtra("indexTest",1);

        CounterForTest timer = new CounterForTest(remainingTime,1000);
        timer.start();

        NextPage nextPage = new NextPage(29000,1000);
        nextPage.start();
        mp3 = MediaPlayer.create(this, R.raw.directionp3);
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
            Intent part3 = new Intent(DirectionPart3.this,Part3.class);
            part3.putExtra("remainingTime",remainingTime);
            part3.putExtra("answerSheet",answerSheet);
            part3.putExtra("audios",audios);
            part3.putExtra("indexTest",indexTest);
            startActivity(part3);
        }
    }
}
