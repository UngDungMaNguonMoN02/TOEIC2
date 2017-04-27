package com.example.lengochoa.toeic;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class AnswerPart1 extends AppCompatActivity {


    private RadioButton a,b,c,d;
    private Button next,pre;
    private TextView txtnoq;
    private int curans;
    private String[] answerSheet, keys, audios;
    private int indexTest;
    private ArrayList<Question> questions;

    private Button btnPlay, btnPause, btnForward, btnRewind;
    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler m_Handler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView txtTimeAudio;

    private ImageSwitcher imageSwitcher;

    public int getResIdByName(String resName)  {
        return getResources().getIdentifier(resName , "drawable", getPackageName());
    }

    private void displayQuestion(){
        String[] ans = questions.get(curans).getAnswer();
        a.setText(ans[0]);
        b.setText(ans[1]);
        c.setText(ans[2]);
        d.setText(ans[3]);
        imageSwitcher.setBackgroundResource(getResIdByName("t" + indexTest + "img" + (curans+1)));
        Animation in= AnimationUtils.loadAnimation(AnswerPart1.this, android.R.anim.slide_in_left);
        in.setDuration(1000);
        imageSwitcher.startAnimation(in);
    }

    private void startAudio(){
        mediaPlayer = new MediaPlayer();
        try
        {
            AssetFileDescriptor afd = getAssets().openFd("audio"+indexTest +"/"+audios[curans]);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        }
        catch (Exception ex) {return;}

        try { mediaPlayer.prepare();}
        catch (Exception ex) {ex.getMessage();}

        finalTime = mediaPlayer.getDuration();
        seekbar.setMax((int) finalTime);
        startTime = mediaPlayer.getCurrentPosition();
        seekbar.setProgress((int)startTime);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        btnPlay.setEnabled(true);
        btnPause.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_part1);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.imgswtch);
        Animation in= AnimationUtils.loadAnimation(AnswerPart1.this, android.R.anim.slide_in_left);
        in.setDuration(1000);
        imageSwitcher.startAnimation(in);

        btnPlay = (Button) findViewById(R.id.btnStart);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnForward = (Button) findViewById(R.id.btnForward);
        btnRewind = (Button) findViewById(R.id.btnRewind);
        txtTimeAudio = (TextView)findViewById(R.id.txtTimeAudio);

        Intent intent = getIntent();
        answerSheet = intent.getStringArrayExtra("answerSheet");
        keys = intent.getStringArrayExtra("ansKey");
        audios= intent.getStringArrayExtra("audios");
        indexTest = intent.getIntExtra("indexTest",1);
        Bundle bundle = getIntent().getExtras();
        questions = (ArrayList<Question>)bundle.getSerializable("questions");

        seekbar = (SeekBar)findViewById(R.id.seekBar);

        btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mediaPlayer.start();
                m_Handler.postDelayed(UpdateSongTime,100);
                btnPause.setEnabled(true);
                btnPlay.setEnabled(false);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mediaPlayer.pause();
                btnPause.setEnabled(false);
                btnPlay.setEnabled(true);
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int temp = (int)startTime;
                if((temp+forwardTime)<=finalTime)
                {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }
            }
        });
        btnRewind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int temp = (int)startTime;

                if((temp-backwardTime)>0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }
            }
        });

        a = (RadioButton)findViewById(R.id.rdba);
        b = (RadioButton)findViewById(R.id.rdbb);
        c = (RadioButton)findViewById(R.id.rdbc);
        d = (RadioButton)findViewById(R.id.rdbd);

        txtnoq = (TextView)findViewById(R.id.txtnoq);
        pre = (Button)findViewById(R.id.btnPre);
        next = (Button)findViewById((R.id.btnNext));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curans<9){
                    curans++;
                    txtnoq.setText(curans+1+"/10");
                    displayQuestion();
                    mediaPlayer.release();
                    startAudio();
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curans>0){
                    curans--;
                    txtnoq.setText(curans+1+"/10");
                    displayQuestion();
                    mediaPlayer.release();
                    startAudio();
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

        curans = 0;
        displayQuestion();
        startAudio();
    }

    private Runnable UpdateSongTime = new Runnable()
    {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            txtTimeAudio.setText(String.format("%d s", TimeUnit.MILLISECONDS.toSeconds((long) startTime)));
            seekbar.setProgress((int)startTime);
            m_Handler.postDelayed(this, 100);
        }
    };
}
