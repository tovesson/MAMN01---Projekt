package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.blowButton);
        b2 = (Button) findViewById(R.id.jumpButton);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Play sound
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.start();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, StartGameActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, JumpActivity.class);
                MainActivity.this.startActivity(myIntent);


        Button jumpMasterButton = findViewById(R.id.jumpMasterButton);
        jumpMasterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), JumpActivity.class);
                startActivity(intent);
            }
        });

        Button throwMasterButton = findViewById(R.id.throwMasterButton);
        throwMasterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ThrowActivity.class);
                startActivity(intent);
            }
        });

        Button highscoreButton = findViewById(R.id.highscoreButton);
        highscoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }

}
