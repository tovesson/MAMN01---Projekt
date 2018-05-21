package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    private TextView headerText;
    private ArrayList<String> listItems;
    private ListView listViewScore;
    private ArrayAdapter<String> adapter;
    private HighScoreDatabase helper;
    private double score;
    private String name;
    private String game;
    private ArrayList<String> scoreList;
    private View view;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_jump:
                    updateHighScore("Jumpmaster");
                    headerText.setText(R.string.slide_2_title);
                    return true;
                case R.id.navigation_throw:
                    updateHighScore("Throwmaster");
                    headerText.setText(R.string.slide_3_title);
                    return true;
                case R.id.navigation_blow:
                    updateHighScore("Blowmaster");
                    headerText.setText(R.string.slide_1_title);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        headerText = (TextView) findViewById(R.id.HighscoreHeaderText);
        Button backToMenu = findViewById(R.id.backToMenu);
        backToMenu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent(HighScoreActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent receiveIntent = getIntent();
        String prevGame = receiveIntent.getStringExtra("game");
        listItems = new ArrayList<String>();
        listViewScore = (ListView) findViewById(R.id.blowScoreList);
        scoreList = new ArrayList<String>();
        helper = new HighScoreDatabase(this);

        if(prevGame.equals("Blowmaster")){
            //change to blowmaster view
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    listItems);
            listViewScore.setAdapter(adapter);
            scoreList = helper.getData(prevGame);
            for (int i = 0; i < scoreList.size(); i++) {
                listItems.add(scoreList.get(i));
            }
            adapter.notifyDataSetChanged();

            view = navigation.findViewById(R.id.navigation_blow);
            view.performClick();
        }else if (prevGame.equals("Throwmaster")){
            //change to throwmaster view
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    listItems);
            listViewScore.setAdapter(adapter);
            scoreList = helper.getData(prevGame);
            for (int i = 0; i < scoreList.size(); i++) {
                listItems.add(scoreList.get(i));
            }
            adapter.notifyDataSetChanged();

            view = navigation.findViewById(R.id.navigation_throw);
            view.performClick();
            Log.d("Clicked", "CLICKED");
        }else {
            //Get database
            scoreList = helper.getData("Jumpmaster");
            for (int i = 0; i < scoreList.size(); i++) {
                listItems.add(scoreList.get(i));
            }
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    listItems);
            listViewScore.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }



    }

    private void updateHighScore(String game){
        adapter.clear();
        scoreList = helper.getData(game);
        for(int i = 0; i < scoreList.size(); i++){
            listItems.add(scoreList.get(i));
        }
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listViewScore.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
