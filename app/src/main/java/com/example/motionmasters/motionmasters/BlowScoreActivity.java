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

public class BlowScoreActivity extends AppCompatActivity {
    private EditText name;
    private TextView result;
    private Button blowScoreButton;
    private double score;
    private String game;
    private DecimalFormat decimalFormat;
    private HighScoreDatabase helper;
    private TextView helpText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_blow_score);
        name = findViewById(R.id.blowName);
        result = findViewById(R.id.blow_ingame_result);
        blowScoreButton = findViewById(R.id.blowScoreButton);
        helpText = findViewById(R.id.helpTextBlow);

        decimalFormat = new DecimalFormat("0.00");
        game = "Blowmaster";
        Intent receiveIntent = getIntent();
        score = receiveIntent.getDoubleExtra("score", 0.0);
        result.setText("You blew for: " + decimalFormat.format((score) / 1000));
        helper = new HighScoreDatabase(this);


        blowScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().length() >= 1) {
                    Intent intent = new Intent(BlowScoreActivity.this, HighScoreActivity.class);
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
