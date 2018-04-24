package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.blowButton);

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
    }

}
