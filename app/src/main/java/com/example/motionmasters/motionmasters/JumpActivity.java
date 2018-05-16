package com.example.motionmasters.motionmasters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class JumpActivity extends AppCompatActivity implements SensorEventListener {
    private double height;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private double velocity;
    private double time = 0.1;
    private double air_time;
    private boolean jumpNow = true;
    private boolean clicked = false;
    private ArrayList<Double> acc_values;
    private double max_value;
    private int bufferSize = 5;
    private boolean highValue;

    private double deltaY = 0;
    private double lastY;

    private MediaPlayer mediaPlayer;

    private Button addBtn;

    private TextView resultTxt;
    private TextView scoreResultTxt;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addBtn = (Button) findViewById(R.id.addResult);
        addBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), JumpScoreActivity.class);
                intent.putExtra("score", "TEST");
                startActivity(intent);

            }
        });
        mediaPlayer = MediaPlayer.create(this, R.raw.bounce);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // fai! we dont have an accelerometer!
        }

        acc_values = new ArrayList<Double>();
        for(int i = 0; i < bufferSize; i++) {
            acc_values.add(deltaY);
        }

        resultTxt = findViewById(R.id.jumpmaster_result);
        scoreResultTxt = findViewById(R.id.jump_ingameres);
        reset = findViewById(R.id.jump_reset);
        highValue = false;
        acc_values.clear();
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onSensorChanged(SensorEvent event) {

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clicked = true;
            }
        });

        if (clicked) {
            jumpNow = true;
            highValue = false;
            resultTxt.setText("Result: " + 0 + "m");
            clicked = false;
        }



        deltaY = Math.abs(lastY - event.values[1]);


        lastY = event.values[1];

        if(deltaY > 8) {
            highValue = true;
            if(mediaPlayer.isPlaying() == false && jumpNow) {
                mediaPlayer.start();
            }
        }


        if (highValue && jumpNow) {
           acc_values.add(deltaY);
           for (int i = 0; i < acc_values.size(); i++) {
               Log.d("ADebugTag", "Value: " + Double.toString(acc_values.get(i)));
           }
           if(acc_values.size()>=bufferSize) {
               max_value = Collections.max(acc_values);

               velocity = ((max_value) * time);
               air_time = (0 - velocity) / (-9.8);
               height = ((velocity * air_time) + (-9.8 * Math.pow(air_time, 2)) / 2);
               double vis_height = (double) Math.round(height * 100) / 100;

               acc_values.clear();

               jumpNow = false;
               highValue = false;

               resultTxt.setText("Result: " + vis_height + "m");
           }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

        Intent myIntent = new Intent(JumpActivity.this, JumpScoreActivity.class);
        JumpActivity.this.startActivity(myIntent);
    }
}