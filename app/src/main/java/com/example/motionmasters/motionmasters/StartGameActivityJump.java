package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StartGameActivityJump extends AppCompatActivity {

    private TextView tv;
    private int switchSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_jump);
        tv = findViewById(R.id.startGameText);
        int secs = 0;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        new CountDownTimer((secs + 1) * 1000, 1000) // Wait 5 secs, tick every 1 sec
        {
            @Override
            public final void onTick(final long millisUntilFinished) {
                switchSec = (int) (millisUntilFinished * .001f);
                switch (switchSec) {
                    case 1:
                        tv.setText("Jumpmaster");
                        break;
                }
            }

            @Override
            public final void onFinish() {
                Intent myIntent = new Intent(StartGameActivityJump.this, JumpActivity.class);
                StartGameActivityJump.this.startActivity(myIntent);
            }
        }.start();
    }
}
