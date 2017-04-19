package com.example.lengochoa.toeic;

import android.content.Intent;
import android.database.DatabaseErrorHandler;
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
    private Question[] questions;
    private SQLiteDatabase database;
    private String dbname = "";

    private void initQuestions(){
//        database = Database.initDatabase(MainActivity.this, dbname);
//        Cursor cursor = database.rawQuery("select * from ",null);
//        while(cursor.moveToNext()){
//
//        }
        questions = new Question[100];
        for(int i = 0; i < 10; i++){
            questions[i] = new Question();
            String[] ans = new String[4];
            ans[0] = "A";
            ans[1] = "B";
            ans[2] = "C";
            ans[3] = "D";
            questions[i].setQuestion("",ans);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData("Chọn test để kiểm tra ...");
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                                "Bạn đã chọn "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition)
                                +". Nhấn nút Start để bắt đầu làm bài", Toast.LENGTH_SHORT).show();
                                expandableListView.collapseGroup(0);
//                                String temp = (String)expandableListView.getSelectedItem();
                return false;
            }
        });

        final Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent part1 = new Intent(MainActivity.this,DirectionPart1.class);
                idxTest=1;
                initQuestions();
                part1.putExtra("indexTest",idxTest);
                part1.putExtra("questions",questions);
                startActivity(part1);
            }
        });
    }

}
