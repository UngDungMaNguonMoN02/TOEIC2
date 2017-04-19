package com.example.lengochoa.toeic;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Part2 extends AppCompatActivity {

    private long remainingTime;
    private String[] answerSheet;
    private TextView txtTimer,txtQuestion;
    private int timeToNext = 0;
    private String tempAns;
    private int currentIndex = 10;
    private MediaPlayer mediaPlayerPart2;
    private int duration;
    private String audios[];
    private int indexTest;

    private String ConvertTime(int s){
        int m = s/60;
        int sec = s%60;
        return " " + (m>9?m:"0"+m) + " : " + (sec>9?sec:"0"+sec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part2);

        Intent intent = getIntent();
        answerSheet = intent.getStringArrayExtra("answerSheet");
        remainingTime = intent.getLongExtra("remainingTime",1L);
        audios = intent.getStringArrayExtra("audios");
        indexTest = intent.getIntExtra("indexTest",1);
        txtTimer = (TextView)findViewById(R.id.txtTimer);
        txtQuestion = (TextView)findViewById(R.id.txtquestion);
        RadioButton a = (RadioButton)findViewById(R.id.rdba);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns="A";
                answerSheet[currentIndex] = tempAns;
            }
        });
        RadioButton b = (RadioButton)findViewById(R.id.rdbb);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns="B";
                answerSheet[currentIndex] = tempAns;
            }
        });
        RadioButton c = (RadioButton)findViewById(R.id.rdbc);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns="C";
                answerSheet[currentIndex] = tempAns;
            }
        });

        playAudio(10);

        CounterForTest timer = new CounterForTest(remainingTime,1000);
        timer.start();

        Counter nextQuestion = new Counter(593000,1000);
        nextQuestion.start();
    }

    public class CounterForTest extends CountDownTimer{

        CounterForTest(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            remainingTime = millisUntilFinished;
            txtTimer.setText(ConvertTime((int)millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {}
    }

    public class Counter extends CountDownTimer{

        Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(timeToNext<=duration+1) timeToNext++;
            else {
                try{
                    currentIndex++;
                    txtQuestion.setText((currentIndex+1)+"/40");
                    answerSheet[currentIndex-1]=tempAns;
                    mediaPlayerPart2.release();
                    playAudio(currentIndex);
                    timeToNext=1;
                }
                catch (Exception ex){ex.printStackTrace();}
            }
        }

        @Override
        public void onFinish() {
            answerSheet[currentIndex]=tempAns;
            mediaPlayerPart2.release();
            Intent part3 = new Intent(Part2.this,DirectionPart3.class);
            part3.putExtra("answerSheet",answerSheet);
            part3.putExtra("remainingTime",remainingTime);
            part3.putExtra("audios",audios);
            part3.putExtra("indexTest",indexTest);
            startActivity(part3);
        }
    }

    private void playAudio(int audioIndex){
        try {
            mediaPlayerPart2 = new MediaPlayer();
            AssetFileDescriptor afd = getAssets().openFd("audio" + indexTest + "/" + audios[audioIndex]);
            mediaPlayerPart2.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayerPart2.prepare();
            duration= mediaPlayerPart2.getDuration()/1000;
            mediaPlayerPart2.start();
        } catch (Exception ex) {ex.printStackTrace();}
    }

}
