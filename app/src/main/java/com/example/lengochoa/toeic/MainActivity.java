package com.example.lengochoa.toeic;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private int idxTest;
    private ArrayList<Question> questions;

    private String dbname = "toeic.sqlite";

    private void initQuestions(){
        SQLiteDatabase database = null;
        questions=new ArrayList<>();
        try{
            database = Database.initDatabase(MainActivity.this, dbname);
            for(int j = 0 ; j < 10 ; j++){
                String query = "INSERT INTO questions (test_id,part_id,data) VALUES(1,1,'...@a"+j+"-b"+j+"-c"+j+"-d"+j+"@A')";
                database.execSQL(query);
            }
            for(int j = 10; j < 40 ; j++){
                String query = "INSERT INTO questions (test_id,part_id,data) VALUES(1,2,'content"+j+"@a"+j+"-b"+j+"-c"+j+"@B')";
                database.execSQL(query);
            }
            for(int j = 40; j < 70 ; j++){
                String query = "INSERT INTO questions (test_id,part_id,data) VALUES(1,3,'content"+j+"@a"+j+"-b"+j+"-c"+j+"-d"+j+"@C')";
                database.execSQL(query);
            }
            for(int j = 70; j < 100 ; j++){
                String query = "INSERT INTO questions (test_id,part_id,data) VALUES(1,4,'content"+j+"@a"+j+"-b"+j+"-c"+j+"-d"+j+"@D')";
                database.execSQL(query);
            }
        } catch (Exception e){Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();}

        for(int idx = 1; idx <= 4; idx++){
            try{
                Cursor cursor = database.rawQuery("select * from questions where test_id = " + idxTest + " and part_id = " + idx ,null);
                while(cursor.moveToNext()){
                    Question temp_q = new Question();
                    String data = cursor.getString(3);
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

        idxTest = 1;

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData("Chọn test để kiểm tra ...");
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

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
                initQuestions();
                Intent direction_part1 = new Intent(MainActivity.this,Part1.class);
                direction_part1.putExtra("indexTest",idxTest);
                Bundle bundle = new Bundle();
                bundle.putSerializable("questions",questions);
                direction_part1.putExtras(bundle);
                startActivity(direction_part1);
            }
        });
    }

}
