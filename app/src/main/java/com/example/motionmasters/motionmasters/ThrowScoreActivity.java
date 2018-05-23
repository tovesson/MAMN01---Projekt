package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ThrowScoreActivity extends AppCompatActivity {
    private EditText name;
    private TextView result;
    private Button throwScoreButton;
    private double score;
    private String game;
    private DecimalFormat decimalFormat;
    private HighScoreDatabase helper;
    private TextView helpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_throw_score);
        name = findViewById(R.id.throwName);
        result = findViewById(R.id.throw_ingame_result);
        throwScoreButton = findViewById(R.id.throwScoreButton);
        helpText = findViewById(R.id.helpTextThrow);

        decimalFormat = new DecimalFormat("0.00");
        game = "Throwmaster";
        Intent receiveIntent = getIntent();
        score = receiveIntent.getDoubleExtra("score", 0.0);
        result.setText("You \"threw\" the phone: " + String.format("%.2f", score) + "m");
        helper = new HighScoreDatabase(this);


        throwScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().length() >= 1) {
                    Intent intent = new Intent(ThrowScoreActivity.this, HighScoreActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("game", game);
                    helper.insertData(name.getText().toString(), score, game);
                    startActivity(intent);
                }else{
                    helpText.setVisibility(TextView.VISIBLE);
                }
            }
        });

    }

}
