package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StartGameActivity extends AppCompatActivity {

    private TextView tv;
    private Handler handler = new Handler();
    private int switchSec;
    private int secs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        tv = (TextView) findViewById(R.id.startGameText);
        int secs = 7;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        new CountDownTimer((secs + 1) * 1000, 1000) // Wait 5 secs, tick every 1 sec
        {
            @Override
            public final void onTick(final long millisUntilFinished) {
                switchSec = (int) (millisUntilFinished * .001f);
                switch (switchSec) {
                    case 7:
                        tv.setText("Blowmaster");
                        break;
                    case 6:
                        tv.setText("Blowmaster");
                        break;
                    case 5:
                        tv.setText("Blow as long as you can");
                        break;
                    case 4:
                        tv.setText("Blow as long as you can");
                        break;
                    case 3:
                        tv.setTextSize(250);
                        tv.setText("3");
                        break;
                    case 2:
                        tv.setTextSize(250);
                        tv.setText("2");
                        break;
                    case 1:
                        tv.setTextSize(250);
                        tv.setText("1");
                        break;
                }
            }

            @Override
            public final void onFinish() {
                Intent myIntent = new Intent(StartGameActivity.this, BlowActivity.class);
                StartGameActivity.this.startActivity(myIntent);
            }
        }.start();
    }
}
