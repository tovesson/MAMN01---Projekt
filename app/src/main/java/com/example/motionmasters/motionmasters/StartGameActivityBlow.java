package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartGameActivityBlow extends AppCompatActivity {

    private TextView tv;
    private int switchSec;
    private Button startBlowGameButton;
    private TextView blowMasterTitle;
    private TextView blowMasterSubtitle;
    private ImageView blowImageDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_blow);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startBlowGameButton = findViewById(R.id.startBlowGameButton);
        blowMasterTitle = findViewById(R.id.blowMasterTitle);
        blowMasterSubtitle = findViewById(R.id.blowMasterSubtitle);
        blowImageDescription = findViewById(R.id.blowImageDescription);

        startBlowGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blowMasterTitle.setVisibility(View.INVISIBLE);
                blowImageDescription.setVisibility(View.INVISIBLE);
                startBlowGameButton.setVisibility(View.INVISIBLE);
                new CountDownTimer((5) * 1000, 1000) // Wait 5 secs, tick every 1 sec
                {
                    @Override
                    public final void onTick(final long millisUntilFinished) {
                        switchSec = (int) (millisUntilFinished * .001f);
                        switch (switchSec) {

                            case 4:
                                blowMasterSubtitle.setTextSize(250);
                                blowMasterSubtitle.setText("3");
                                break;
                            case 3:
                                blowMasterSubtitle.setTextSize(250);
                                blowMasterSubtitle.setText("2");
                                break;
                            case 2:
                                blowMasterSubtitle.setTextSize(250);
                                blowMasterSubtitle.setText("1");
                                break;
                            case 1:
                                blowMasterSubtitle.setTextSize(150);
                                blowMasterSubtitle.setText("Blow");
                                break;
                        }
                    }

                    @Override
                    public final void onFinish() {
                        Intent myIntent = new Intent(StartGameActivityBlow.this, BlowActivity.class);
                        StartGameActivityBlow.this.startActivity(myIntent);
                    }
                }.start();

            }
        });

    }
}
