package com.example.lengochoa.toeic;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Part3 extends AppCompatActivity {

    private RadioButton a1,b1,c1,d1,a2,b2,c2,d2,a3,b3,c3,d3;
    private TextView txtQ1,txtQ2,txtQ3,txtTimer;
    private long remainingTime;
    private String[] answerSheet;
    private MediaPlayer mediaPlayerPart3;
    private int duration=0;
    private int timeToNext=0;
    private int currentIndex;
    private String tempAns1="";
    private String tempAns2="";
    private String tempAns3="";
    private String[] audios;
    private int indexTest;
    private ArrayList<Question> questions;
    private Button nextPart;
    private Typeface typeface;
    private String ConvertTime(int s){
        int m = s/60;
        int sec = s%60;
        return " " + (m>9?m:"0"+m) + " : " + (sec>9?sec:"0"+sec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part3);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Fun Smiles.ttf");
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Sweet Sensations Personal Use.ttf");

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
        txtTimer.setTypeface(custom_font);

        Intent intent = getIntent();
        remainingTime = intent.getLongExtra("remaingTime",1L);
        answerSheet = intent.getStringArrayExtra("answerSheet");
        audios = intent.getStringArrayExtra("audios");
        indexTest = intent.getIntExtra("indexTest",1);
        Bundle bundle = getIntent().getExtras();
        questions = (ArrayList<Question>) bundle.getSerializable("questions");

        final CounterForTest timer = new CounterForTest(1880000,1000);
        timer.start();

        currentIndex= 40;
        displayQuestion(currentIndex);
        playAudio(currentIndex);

        final Counter nextQuestion = new Counter(648000,1000);
        nextQuestion.start();

        nextPart = (Button)findViewById(R.id.btnNextPart);
        nextPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                nextQuestion.cancel();
                mediaPlayerPart3.release();
//                for(int i = 40; i < 70; i++){
//                    Random random = new Random();
//                    int n = random.nextInt(3);
//                    if(n==0) answerSheet[i]="A";
//                    else if(n==1) answerSheet[i]="B";
//                    else if(n==2) answerSheet[i]="C";
//                    else answerSheet[i]="D";
//                }
                Intent part4 = new Intent(Part3.this,Part4.class);
                part4.putExtra("answerSheet",answerSheet);
                part4.putExtra("remainingTime",remainingTime);
                part4.putExtra("audios",audios);
                part4.putExtra("indexTest",indexTest);
                Bundle bundle = new Bundle();
                bundle.putSerializable("questions",questions);
                part4.putExtras(bundle);
                startActivity(part4);
            }
        });

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
            if(timeToNext<=duration+1) timeToNext++;
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
            Intent direction_part4 = new Intent(Part3.this,DirectionPart4.class);
            direction_part4.putExtra("answerSheet",answerSheet);
            direction_part4.putExtra("remainingTime",remainingTime);
            direction_part4.putExtra("audios",audios);
            direction_part4.putExtra("indexTest",indexTest);
            Bundle bundle = new Bundle();
            bundle.putSerializable("questions",questions);
            direction_part4.putExtras(bundle);
            startActivity(direction_part4);
        }
    }

    private void displayQuestion(int questionIndex){
        try{
            txtQ1.setText((questionIndex+1) + ". " + questions.get(questionIndex).getContent());
            txtQ1.setTypeface(typeface);
            String[] temp = questions.get(questionIndex).getAnswer();
            a1.setText(temp[0]);
            b1.setText(temp[1]);
            c1.setText(temp[2]);
            d1.setText(temp[3]);

            txtQ2.setText((questionIndex+2) + ". " + questions.get(questionIndex+1).getContent());
            txtQ2.setTypeface(typeface);
            temp = questions.get(questionIndex+1).getAnswer();
            a2.setText(temp[0]);
            b2.setText(temp[1]);
            c2.setText(temp[2]);
            d2.setText(temp[3]);

            txtQ3.setText((questionIndex+3) + ". " + questions.get(questionIndex+2).getContent());
            txtQ3.setTypeface(typeface);
            temp = questions.get(questionIndex+2).getAnswer();
            a3.setText(temp[0]);
            b3.setText(temp[1]);
            c3.setText(temp[2]);
            d3.setText(temp[3]);
        }
        catch (Exception e){
            Toast.makeText(
                    getApplicationContext(),
                    "false", Toast.LENGTH_SHORT).show();
        }
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
