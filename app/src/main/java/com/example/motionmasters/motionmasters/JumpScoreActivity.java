package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by arpi on 2018-05-16.
 */

public class JumpScoreActivity extends AppCompatActivity {

    private Button jumpScoreButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingamescore_jump);
        jumpScoreButton = findViewById(R.id.add_result_jump);

        jumpScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JumpScoreActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
