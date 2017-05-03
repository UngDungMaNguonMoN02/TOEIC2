package com.example.lengochoa.toeic;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.w3c.dom.Text;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private int idxTest;
    private ArrayList<Question> questions;
    private TextView txtStrong;

    private String dbname = "toeic1.sqlite";

    private void initQuestions(){
        SQLiteDatabase database = null;
        questions=new ArrayList<>();
        try{
            database = Database.initDatabase(MainActivity.this, dbname);
        } catch (Exception e){Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();}

        for(int idx = 1; idx <= 4; idx++){
            try{
                Cursor cursor = database.rawQuery("select * from questions where test_id = " + idxTest + " and part_id = " + idx ,null);
                while(cursor.moveToNext()){
                    Question temp_q = new Question();
                    String data = cursor.getString(2).trim();
                    String[] temp = data.split("@");
                    String[] ans = temp[1].split("-");
                    temp_q.setQuestion(temp[0],ans,temp[2],((idx==2)?3:4));
                    questions.add(temp_q);
                }
                cursor.close();
            } catch (NullPointerException e){return;}
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idxTest = -1;
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData("Chọn test để kiểm tra ...");
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        txtStrong = (TextView)findViewById(R.id.txtStrong);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/KBZipaDeeDooDah.ttf");
        txtStrong.setTypeface(custom_font);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(
                    ExpandableListView parent,
                    View v,
                    int groupPosition,
                    int childPosition,
                    long id) {
                idxTest = childPosition + 1;
                Toast.makeText(
                        getApplicationContext(),
                                "Bạn đã chọn "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT).show();
                                expandableListView.collapseGroup(0);
                return false;
            }
        });

        final Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(idxTest!=-1){
                    initQuestions();
                    Intent direction_part1 = new Intent(MainActivity.this,Part1.class);
                    direction_part1.putExtra("indexTest",idxTest);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("questions",questions);
                    direction_part1.putExtras(bundle);
                    startActivity(direction_part1);
                }
                else {
                    Toast.makeText( getApplicationContext(), "Bạn chưa chọn test nào !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
