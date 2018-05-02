package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by danieltovesson on 2018-05-02.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Play sound
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.start();

        // Start main activity and close splash activity
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
