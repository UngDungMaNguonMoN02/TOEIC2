package com.example.lengochoa.toeic;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AnswerPart4 extends AppCompatActivity {

    private RadioButton a1,b1,c1,d1;
    private RadioButton a2,b2,c2,d2;
    private RadioButton a3,b3,c3,d3;
    private Button next,pre;
    private TextView txtquestion1,txtquestion2,txtquestion3;
    private int curans;
    private int cur_audio;
    private String[] answerSheet, keys, audios;
    private int indexTest;
    private ArrayList<Question> questions;

    private Button btnPlay;
    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler m_Handler = new Handler();
    private SeekBar seekbar;
    private TextView txtTimeAudio;
    private Button btnMenu;
    private boolean run;

    private String ConvertTime(int s){
        int m = s/60;
        int sec = s%60;
        return (m>9?m:"0"+m) + ":" + (sec>9?sec:"0"+sec);
    }

    private void displayQuestion(Object a,Object b,Object c,Object d,Object txtquestion,int offset){
        String question = questions.get(curans+offset).getContent();
        ((TextView)txtquestion).setText((curans+offset+1) + " . " + question);
        String[] ans = questions.get(curans+offset).getAnswer();
        ((RadioButton)a).setText(ans[0]);
        ((RadioButton)b).setText(ans[1]);
        ((RadioButton)c).setText(ans[2]);
        ((RadioButton)d).setText(ans[3]);
        ((RadioButton)a).setTextColor(Color.BLACK);
        ((RadioButton)b).setTextColor(Color.BLACK);
        ((RadioButton)c).setTextColor(Color.BLACK);
        ((RadioButton)d).setTextColor(Color.BLACK);

        switch (answerSheet[curans+offset]){
            case "A": ((RadioButton)a).setChecked(true);break;
            case "B": ((RadioButton)b).setChecked(true);break;
            case "C": ((RadioButton)c).setChecked(true);break;
            case "D": ((RadioButton)d).setChecked(true);break;
            default:
                ((RadioButton)a).setChecked(false);
                ((RadioButton)b).setChecked(false);
                ((RadioButton)c).setChecked(false);
                ((RadioButton)d).setChecked(false);break;
        }

        if(!answerSheet[curans+offset].equals(keys[curans+offset])){
            switch (answerSheet[curans+offset]){
                case "A":
                    ((RadioButton)a).setText(((RadioButton)a).getText() + " " + getString(R.string.wrong));
                    ((RadioButton)a).setTextColor(Color.rgb(188,42,70));break;
                case "B":
                    ((RadioButton)b).setText(((RadioButton)b).getText() + " " + getString(R.string.wrong));
                    ((RadioButton)b).setTextColor(Color.rgb(188,42,70));break;
                case "C":
                    ((RadioButton)c).setText(((RadioButton)c).getText() + " " + getString(R.string.wrong));
                    ((RadioButton)c).setTextColor(Color.rgb(188,42,70));break;
                case "D":
                    ((RadioButton)d).setText(((RadioButton)d).getText() + " " + getString(R.string.wrong));
                    ((RadioButton)d).setTextColor(Color.rgb(188,42,70));break;
                default:break;
            }
        }
        else{
            switch (answerSheet[curans+offset]){
                case "A":
                    ((RadioButton)a).setText(((RadioButton)a).getText() + " " + getString(R.string.right));;break;
                case "B":
                    ((RadioButton)b).setText(((RadioButton)b).getText() + " " + getString(R.string.right));break;
                case "C":
                    ((RadioButton)c).setText(((RadioButton)c).getText() + " " + getString(R.string.right));break;
                case "D":
                    ((RadioButton)d).setText(((RadioButton)d).getText() + " " + getString(R.string.wrong));
                    ((RadioButton)d).setTextColor(Color.rgb(188,42,70));break;
                default:break;
            }
        }

        switch (keys[curans+offset]){
            case"A":
                ((RadioButton)a).setTextColor(Color.rgb(95,139,101));break;
            case "B":
                ((RadioButton)b).setTextColor(Color.rgb(95,139,101));break;
            case "C":
                ((RadioButton)c).setTextColor(Color.rgb(95,139,101));break;
            case "D":
                ((RadioButton)d).setTextColor(Color.rgb(95,139,101));break;
            default:break;
        }
    }

    private void startAudio(){
        mediaPlayer = new MediaPlayer();
        run = true;
        btnPlay.setText(getString(R.string.play));
        try
        {
            AssetFileDescriptor afd = getAssets().openFd("audio"+indexTest +"/"+audios[cur_audio]);
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
        setContentView(R.layout.activity_answer_part4);

        btnPlay = (Button) findViewById(R.id.btnStart);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        txtTimeAudio = (TextView)findViewById(R.id.txtTimeAudio);
        txtquestion1 = (TextView)findViewById(R.id.txtquestion1);
        txtquestion2 = (TextView)findViewById(R.id.txtquestion2);
        txtquestion3 = (TextView)findViewById(R.id.txtquestion3);
        a1 = (RadioButton)findViewById(R.id.rdba1);
        b1 = (RadioButton)findViewById(R.id.rdbb1);
        c1 = (RadioButton)findViewById(R.id.rdbc1);
        d1 = (RadioButton)findViewById(R.id.rdbd1);

        a2 = (RadioButton)findViewById(R.id.rdba2);
        b2 = (RadioButton)findViewById(R.id.rdbb2);
        c2 = (RadioButton)findViewById(R.id.rdbc2);
        d2 = (RadioButton)findViewById(R.id.rdbd2);

        a3 = (RadioButton)findViewById(R.id.rdba3);
        b3 = (RadioButton)findViewById(R.id.rdbb3);
        c3 = (RadioButton)findViewById(R.id.rdbc3);
        d3 = (RadioButton)findViewById(R.id.rdbd3);

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
                txtTimeAudio.setText(ConvertTime((int)(startTime/1000)));
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

        pre = (Button)findViewById(R.id.btnPre);
        next = (Button)findViewById((R.id.btnNext));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curans<97){
                    curans+=3;
                    cur_audio++;
                    displayQuestion(a1,b1,c1,d1,txtquestion1,0);
                    displayQuestion(a2,b2,c2,d2,txtquestion2,1);
                    displayQuestion(a3,b3,c3,d3,txtquestion3,2);
                    mediaPlayer.release();
                    startAudio();
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curans>70){
                    curans-=3;
                    cur_audio--;
                    displayQuestion(a1,b1,c1,d1,txtquestion1,0);
                    displayQuestion(a2,b2,c2,d2,txtquestion2,1);
                    displayQuestion(a3,b3,c3,d3,txtquestion3,2);
                    mediaPlayer.release();
                    startAudio();
                }
            }
        });

        curans = 70;
        cur_audio = 50;
        displayQuestion(a1,b1,c1,d1,txtquestion1,0);
        displayQuestion(a2,b2,c2,d2,txtquestion2,1);
        displayQuestion(a3,b3,c3,d3,txtquestion3,2);
        startAudio();

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                m_Handler.removeCallbacks(updateSongTime);
                Intent menu = new Intent(AnswerPart4.this,Menu.class);
                menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
            }
        });
    }
}
