package com.example.lengochoa.toeic;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class AnswerPart1 extends AppCompatActivity {


    private RadioButton a,b,c,d;
    private Button next,pre;
    private TextView txtnoq;
    private int curans;
    private VideoView videoView;
    private int pos;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_part1);

        curans = 0;
//        Intent intent = getIntent();
//        answerSheet = intent.getStringArrayExtra("answerSheet");
//        keys = intent.getStringArrayExtra("ansKey");
//        audios= intent.getStringArrayExtra("audios");
//        indexTest = intent.getIntExtra("indexTest",1);
//        Bundle bundle = getIntent().getExtras();
//        questions = (ArrayList<Question>)bundle.getSerializable("questions");

        videoView = (VideoView)findViewById(R.id.videoView);
        //Tao media Controller
        if(mediaController == null){
            mediaController = new MediaController(AnswerPart1.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
        }

        try{
            videoView.setVideoPath("file:///android_asset/audio1/a01.mp3");
            videoView.requestFocus();
            videoView.start();
            pos = 0 ;
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.seekTo(pos);
                if(pos == 0){
                    videoView.start();
                }
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                        mediaController.setAnchorView(videoView);
                    }
                });
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
                }
            }
        });

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curans>0){
                    curans--;
                    txtnoq.setText(curans+1+"/10");
                }
            }
        });

        }
}
