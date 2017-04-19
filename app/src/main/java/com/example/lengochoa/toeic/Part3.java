package com.example.lengochoa.toeic;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Part3 extends AppCompatActivity {

    private RadioButton a1,b1,c1,d1,a2,b2,c2,d2,a3,b3,c3,d3;
    private TextView txtQ1,txtQ2,txtQ3,txtTimer;
    private long remainingTime;
    private String[] answerSheet;
    private MediaPlayer mediaPlayerPart3;
    private int duration=0;
    private int timeToNext=0;
    private int currentIndex=40;
    private String tempAns1="";
    private String tempAns2="";
    private String tempAns3="";
    private String[] audios;
    private int indexTest;
    private Question[] questions;

    private void InitQuestion(){
        questions = new Question[30];
        for(int i = 0;i<30;i++){
            questions[i] = new Question();
        }
        String[] content = new String[30];
        String[] ans = new String[120];

        content[0] = "Where are the speakers going?";
        ans[0] = "To the parade";
        ans[1] = "To a doctor's appointment";
        ans[2] = "To a conference";
        ans[3] = "To the university";

        content[1] = "What are the speakers discussing?";
        ans[4] = "How to get to the conference";
        ans[5] = "How will drive to the conference";
        ans[6] = "The theme of the parade";
        ans[7] = "Where their office is located";

        content[2] = "What does the men suggest?";
        ans[8] = "Drive there";
        ans[9] = "Take the bus";
        ans[10] = "Take the subway";
        ans[11] = "Take a taxi";

        for(int i = 0; i < 30 ; i++)
        {
            String[] temp = new String[4];
            temp[0] = ans[i * 4];
            temp[1] = ans[i * 4 + 1];
            temp[2] = ans[i * 4 + 2];
            temp[3] = ans[i * 4 + 3];
            questions[i].setQuestion(content[i],temp);
        }
    }

    private String ConvertTime(int s){
        int m = s/60;
        int sec = s%60;
        return " " + (m>9?m:"0"+m) + " : " + (sec>9?sec:"0"+sec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part3);
        InitQuestion();

        a1=(RadioButton)findViewById(R.id.rdba1);
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns1="A";
                answerSheet[currentIndex]=tempAns1;
            }
        });
        b1=(RadioButton)findViewById(R.id.rdbb1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns1="B";
                answerSheet[currentIndex]=tempAns1;
            }
        });
        c1=(RadioButton)findViewById(R.id.rdbc1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns1="C";
                answerSheet[currentIndex]=tempAns1;
            }
        });
        d1=(RadioButton)findViewById(R.id.rdbd1);
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns1="D";
                answerSheet[currentIndex]=tempAns1;
            }
        });

        a2=(RadioButton)findViewById(R.id.rdba2);
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns2="A";
                answerSheet[currentIndex+1]=tempAns2;
            }
        });
        b2=(RadioButton)findViewById(R.id.rdbb2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns2="B";
                answerSheet[currentIndex+1]=tempAns2;
            }
        });
        c2=(RadioButton)findViewById(R.id.rdbc2);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns2="C";
                answerSheet[currentIndex+1]=tempAns2;
            }
        });
        d2=(RadioButton)findViewById(R.id.rdbd2);
        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns2="D";
                answerSheet[currentIndex+1]=tempAns2;
            }
        });

        a3=(RadioButton)findViewById(R.id.rdba3);
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns3="A";
                answerSheet[currentIndex+2]=tempAns3;
            }
        });
        b3=(RadioButton)findViewById(R.id.rdbb3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns3="B";
                answerSheet[currentIndex+2]=tempAns3;
            }
        });
        c3=(RadioButton)findViewById(R.id.rdbc3);
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns3="C";
                answerSheet[currentIndex+2]=tempAns3;
            }
        });
        d3=(RadioButton)findViewById(R.id.rdbd3);
        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempAns3="D";
                answerSheet[currentIndex+2]=tempAns3;
            }
        });

        txtQ1 = (TextView)findViewById(R.id.txtquestion1);
        txtQ2 = (TextView)findViewById(R.id.txtquestion2);
        txtQ3 = (TextView)findViewById(R.id.txtquestion3);
        txtTimer = (TextView)findViewById(R.id.txtTimer);

        Intent intent = getIntent();
        remainingTime = intent.getLongExtra("remaingTime",1L);
        answerSheet = intent.getStringArrayExtra("answerSheet");
        audios = intent.getStringArrayExtra("audios");
        indexTest = intent.getIntExtra("indexTest",1);


        AssetManager assetManager = getAssets();
        try{
            audios = assetManager.list("audio" + indexTest);
        } catch (Exception ex){ex.printStackTrace();}

        CounterForTest timer = new CounterForTest(remainingTime,1000);
        timer.start();

        displayQuestion(currentIndex);
        playAudio(currentIndex);

        Counter nextQuestion = new Counter(648000,1000);
        nextQuestion.start();

    }
    public class CounterForTest extends CountDownTimer {

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
            if(timeToNext<=duration) timeToNext++;
            else {
                try{
                    currentIndex+=3;
                    answerSheet[currentIndex-3]=tempAns1;
                    answerSheet[currentIndex-2]=tempAns2;
                    answerSheet[currentIndex-1]=tempAns3;
                    displayQuestion(currentIndex);
                    playAudio(currentIndex-2);
                    timeToNext=1;
                }
                catch (Exception ex){ex.printStackTrace();}
            }
        }

        @Override
        public void onFinish() {
            answerSheet[currentIndex]=tempAns1;
            answerSheet[currentIndex+1]=tempAns2;
            answerSheet[currentIndex+2]=tempAns3;
            mediaPlayerPart3.release();
            Intent part4 = new Intent(Part3.this,DirectionPart4.class);
            part4.putExtra("answerSheet",answerSheet);
            part4.putExtra("remainingTime",remainingTime);
            part4.putExtra("audios",audios);
            part4.putExtra("indexTest",indexTest);
            startActivity(part4);
        }
    }

    private void displayQuestion(int questionIndex){
        txtQ1.setText((questionIndex+1) + ". " + questions[questionIndex-40].getContent());
        String[] temp = questions[questionIndex-40].getAnswer();
        a1.setText(temp[0]);
        b1.setText(temp[1]);
        c1.setText(temp[2]);
        d1.setText(temp[3]);

        txtQ2.setText((questionIndex+2) + ". " + questions[questionIndex-39].getContent());
        temp = questions[questionIndex-39].getAnswer();
        a2.setText(temp[0]);
        b2.setText(temp[1]);
        c2.setText(temp[2]);
        d2.setText(temp[3]);

        txtQ3.setText((questionIndex+3) + ". " + questions[questionIndex-38].getContent());
        temp = questions[questionIndex-38].getAnswer();
        a3.setText(temp[0]);
        b3.setText(temp[1]);
        c3.setText(temp[2]);
        d3.setText(temp[3]);
    }

    private void playAudio(int audioIndex){
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio" + indexTest + "/" + audios[audioIndex]);
            mediaPlayerPart3 = new MediaPlayer();
            mediaPlayerPart3.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayerPart3.prepare();
            duration= mediaPlayerPart3.getDuration()/1000;
            mediaPlayerPart3.start();
        } catch (Exception ex) {ex.printStackTrace();}
    }

}
