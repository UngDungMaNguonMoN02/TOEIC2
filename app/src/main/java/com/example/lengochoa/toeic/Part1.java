package com.example.lengochoa.toeic;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.animation.*;
import android.widget.*;
import java.lang.*;
import android.media.MediaPlayer;

public class Part1 extends AppCompatActivity{

    private int indexTest;
    private TextView txtTimer,txtnoq;
    private ImageSwitcher imgSwitcher;
    private int timeNext = 1;
    private int currentIndex = 0;
    private long remainingTime;
    private int duration;
    private String currentAns="";
    private String[] answerSheet;
    private String[] audios;
    private Question[] questions;
    private MediaPlayer mediaPlayerPart1;

    private String ConvertTime(int s){
        int m = s/60;
        int sec = s%60;
        return " " + (m>9?m:"0"+m) + " : " + (sec>9?sec:"0"+sec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part1);

        txtnoq = (TextView)findViewById(R.id.txtnoq);
        Intent intent = getIntent();
        indexTest = intent.getIntExtra("indexTest",1);
        remainingTime = intent.getLongExtra("remainingTime",1L);
        questions = (Question[])intent.getSerializableExtra("questions");
        AssetManager assetManager = getAssets();
        try{
            audios = assetManager.list("audio"+indexTest);
        } catch (Exception ex){}

        answerSheet = new String[100];
        RadioButton a = (RadioButton)findViewById(R.id.rdba);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAns="A";
                answerSheet[currentIndex] = currentAns;
            }
        });
        RadioButton b = (RadioButton)findViewById(R.id.rdbb);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAns="B";
                answerSheet[currentIndex] = currentAns;
            }
        });
        RadioButton c = (RadioButton)findViewById(R.id.rdbc);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAns="C";
                answerSheet[currentIndex] = currentAns;
            }
        });
        RadioButton d = (RadioButton)findViewById(R.id.rdbd);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAns="D";
                answerSheet[currentIndex] = currentAns;
            }
        });

        txtTimer = (TextView)findViewById(R.id.txtTimer);
        txtnoq = (TextView)findViewById(R.id.txtnoq);
        txtnoq.setText("1/10");

        imgSwitcher = (ImageSwitcher) findViewById(R.id.imgswtch);
        Animation in= AnimationUtils.loadAnimation(Part1.this, android.R.anim.slide_in_left);
        in.setDuration(1000);
        imgSwitcher.startAnimation(in);

        showImage(1);
        playAudio(0);

        CounterForTest timer = new CounterForTest(remainingTime,1000);
        timer.start();
        SwitchImages part1 = new SwitchImages(227000,1000);
        part1.start();
    }

    public class CounterForTest extends CountDownTimer{

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

    public class SwitchImages extends CountDownTimer{

        SwitchImages(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(timeNext<duration+8) timeNext++;
            else {
                try{
                    currentIndex++;
                    txtnoq.setText((currentIndex+1)+"/10");
                    answerSheet[currentIndex-1]=currentAns;
                    mediaPlayerPart1.release();
                    showImage(currentIndex+1);
                    mediaPlayerPart1.release();
                    playAudio(currentIndex);
                    timeNext=1;
                }
                catch (Exception ex){ex.printStackTrace();}
            }
        }

        @Override
        public void onFinish() {
            mediaPlayerPart1.release();
            answerSheet[currentIndex]=currentAns;
            Intent part2 = new Intent(Part1.this,DirectionPart2.class);
            part2.putExtra("answerSheet",answerSheet);
            part2.putExtra("remainingTime",remainingTime);
            part2.putExtra("audios",audios);
            part2.putExtra("indexTest",indexTest);
            startActivity(part2);
        }
    }

    private void showImage(int imgIndex)  {
        Animation in= AnimationUtils.loadAnimation(Part1.this, android.R.anim.slide_in_left);
        in.setDuration(1000);
        imgSwitcher.startAnimation(in);
        imgSwitcher.setBackgroundResource(getResIdByName("t"+indexTest+"img"+imgIndex));
    }

    public int getResIdByName(String resName)  {
        return getResources().getIdentifier(resName , "drawable", getPackageName());
    }

    private void playAudio(int audioIndex){
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio" + indexTest + "/" + audios[audioIndex]);
            mediaPlayerPart1 = new MediaPlayer();
            mediaPlayerPart1.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayerPart1.prepare();
            duration= mediaPlayerPart1.getDuration()/1000;
            mediaPlayerPart1.start();
        } catch (Exception ex) {ex.printStackTrace();}
    }
}
