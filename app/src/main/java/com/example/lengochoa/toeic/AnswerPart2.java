package com.example.lengochoa.toeic;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class AnswerPart2 extends AppCompatActivity {

    private RadioButton a,b,c;
    private Button next,pre;
    private TextView txtnoq;
    private TextView txtquestion;
    private int curans;
    private String[] answerSheet, keys, audios;
    private int indexTest;
    private ArrayList<Question> questions;

    private Button btnPlay;
    private MediaPlayer mediaPlayer;
    private Typeface custom_font;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler m_Handler = new Handler();
    private SeekBar seekbar;
    private TextView txtTimeAudio;
    private Button btnMenu;
    private boolean run;

    private String addLetterSpacing(String question){
        String[] temp = question.split(" ");
        question="";
        for (String s:temp) {
            question+=s+"  ";
        }
        return question.trim();
    }

    private void displayQuestion(){
        String question = questions.get(curans).getContent();
        txtquestion.setText(addLetterSpacing(question));
        txtquestion.setTypeface(custom_font);
        String[] ans = questions.get(curans).getAnswer();
        a.setText(ans[0]);
        b.setText(ans[1]);
        c.setText(ans[2]);
        a.setTextColor(Color.BLACK);
        b.setTextColor(Color.BLACK);
        c.setTextColor(Color.BLACK);

        switch (answerSheet[curans]){
            case "A": a.setChecked(true);break;
            case "B": b.setChecked(true);break;
            case "C": c.setChecked(true);break;
            default:
                a.setChecked(false);
                b.setChecked(false);
                c.setChecked(false);
                break;
        }

        if(!answerSheet[curans].equals(keys[curans])){
            switch (answerSheet[curans]){
                case "A":
                    a.setText(a.getText() + " " + getString(R.string.wrong));
                    a.setTextColor(Color.rgb(188,42,70));break;
                case "B":
                    b.setText(b.getText() + " " + getString(R.string.wrong));
                    b.setTextColor(Color.rgb(188,42,70));break;
                case "C":
                    c.setText(c.getText() + " " + getString(R.string.wrong));
                    c.setTextColor(Color.rgb(188,42,70));break;
                default:break;
            }
        }
        else{
            switch (answerSheet[curans]){
                case "A":
                    a.setText(a.getText() + " " + getString(R.string.right));;break;
                case "B":
                    b.setText(b.getText() + " " + getString(R.string.right));break;
                case "C":
                    c.setText(c.getText() + " " + getString(R.string.right));break;
                default:break;
            }
        }

        switch (keys[curans]){
            case"A":
                a.setTextColor(Color.rgb(95,139,101));break;
            case "B":
                b.setTextColor(Color.rgb(95,139,101));break;
            case "C":
                c.setTextColor(Color.rgb(95,139,101));break;
            default:break;
        }
    }

    private void startAudio(){
        mediaPlayer = new MediaPlayer();
        run = true;
        btnPlay.setText(getString(R.string.play));
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_part2);

        btnPlay = (Button) findViewById(R.id.btnStart);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        txtTimeAudio = (TextView)findViewById(R.id.txtTimeAudio);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Sweet Sensations Personal Use.ttf");
        txtquestion = (TextView)findViewById(R.id.txtquestion);
        a = (RadioButton)findViewById(R.id.rdba);
        b = (RadioButton)findViewById(R.id.rdbb);
        c = (RadioButton)findViewById(R.id.rdbc);

        Intent intent = getIntent();
        answerSheet = intent.getStringArrayExtra("answerSheet");
        keys = intent.getStringArrayExtra("ansKey");
        audios= intent.getStringArrayExtra("audios");
        indexTest = intent.getIntExtra("indexTest",1);
        Bundle bundle = getIntent().getExtras();
        questions = (ArrayList<Question>)bundle.getSerializable("questions");


        seekbar = (SeekBar)findViewById(R.id.seekBar);

        final Runnable updateSongTime = new Runnable() {
            @Override
            public void run() {
                startTime = mediaPlayer.getCurrentPosition();
                txtTimeAudio.setText(String.format("0:%d", TimeUnit.MILLISECONDS.toSeconds((long) startTime)));
                seekbar.setProgress((int)startTime);
                m_Handler.postDelayed(this, 100);
            }
        };

        btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(run){
                    mediaPlayer.start();
                    m_Handler.postDelayed(updateSongTime,100);
                    btnPlay.setText(getString(R.string.pause));
                    run=false;
                }
                else{
                    mediaPlayer.pause();
                    btnPlay.setText(getString(R.string.play));
                    run=true;
                }
            }
        });


        txtnoq = (TextView)findViewById(R.id.txtnoq);
        pre = (Button)findViewById(R.id.btnPre);
        next = (Button)findViewById((R.id.btnNext));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curans<39){
                    curans++;
                    txtnoq.setText(curans+1+"/40");
                    displayQuestion();
                    mediaPlayer.release();
                    startAudio();
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curans>10){
                    curans--;
                    txtnoq.setText(curans+1+"/40");
                    displayQuestion();
                    mediaPlayer.release();
                    startAudio();
                }
            }
        });

        curans = 10;
        displayQuestion();
        startAudio();

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                m_Handler.removeCallbacks(updateSongTime);
                Intent menu = new Intent(AnswerPart2.this,Menu.class);
                menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
            }
        });
    }
}
