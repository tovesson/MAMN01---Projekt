package com.example.motionmasters.motionmasters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class JumpActivity extends AppCompatActivity implements SensorEventListener {
    private double height;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private double velocity;
    private double time = 0.08;
    private double air_time;
    private boolean jumpNow = true;
    private boolean clicked=false;
    private double[] height_array;

    private float deltaY = 0;
    private float lastY;


    private TextView resultTxt;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // fai! we dont have an accelerometer!
        }

        resultTxt = findViewById(R.id.jumpmaster_result);
        reset = findViewById(R.id.jump_reset);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onSensorChanged(SensorEvent event){

        //my button clic
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //change boolean value
                clicked=true;
            }
        });

        //then on another method or where you want
        if(clicked)
        {
            jumpNow = true;
            resultTxt.setText("Result: " + 0);
            clicked = false;
        }

        // get the change of the x,y,z values of the accelerometer

        deltaY = Math.abs(lastY - event.values[1]);

        // if the change is below 2, it is just plain noise

        // set the last know values of x,y,z
        lastY = event.values[1];


        if(deltaY > 15 && jumpNow) {
            velocity = ((deltaY) * time);
            air_time = (0 - velocity) / (-9.8);
            height = ((velocity * air_time) + (-9.8 * Math.pow(air_time, 2)) / 2);
            double vis_height = (double) Math.round(height * 100) / 100;
            resultTxt.setText("Result: " + vis_height +"m");
            jumpNow = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

        Intent myIntent = new Intent(JumpActivity.this, MainActivity.class);
        JumpActivity.this.startActivity(myIntent);
    }
}