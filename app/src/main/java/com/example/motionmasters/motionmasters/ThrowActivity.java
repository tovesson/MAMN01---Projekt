package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;


public class ThrowActivity extends AppCompatActivity implements SensorEventListener
{
    private TextView gyroText;
    private SensorManager sManager;
    private TreeMap<Double, ArrayList<Integer>> valueArray;
    private int counter;
    private double velocity;
    private double angle;
    private double time;
    private double timestamp;
    private ArrayList list;
    private long lastDown;
    private long lastDuration;
    private ArrayList startValues;
    private ArrayList endValues;
    private SensorEvent clickEvent;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw);
        gyroText = (TextView) findViewById(R.id.textTest);
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Button startThrowButton = findViewById(R.id.startThrowButton);
        Button throwButton = findViewById(R.id.throwButton);
        throwButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    lastDown = System.currentTimeMillis();
                    startValues = list;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    lastDuration = System.currentTimeMillis() - lastDown;
                    endValues = list;
                    gyroText.setText(Float.toString((Float)startValues.get(0)) + ", " + Float.toString((Float)startValues.get(1)) +", " + Float.toString((Float)endValues.get(0))+ ", " + Float.toString((Float)endValues.get(1)));
                    //gyroText.setText(Double.toString(calcRotationAngleInDegrees(startValues,endValues)));
                }
                return true;
            }
        });

        startThrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateLength();
            }
        });
        counter = 0;
        velocity = 10;
        angle = 53;
        time = 1.6;
        timestamp = 0;
        list = new ArrayList<Integer>();
        valueArray = new TreeMap<Double, ArrayList<Integer>>();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onStop()
    {
        sManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }
        list.clear();
        System.out.print(list.size());
        Log.d("time", Integer.toString(list.size()));
        timestamp = System.currentTimeMillis();
        //list.add(event.values[2]);
        list.add(event.values[0]);
        list.add(event.values[1]);
        valueArray.put(timestamp,list);

        //gyroText.setText("X value: "+ Float.toString(event.values[2]) +"\n"+
        //        "Y value: "+ Float.toString(event.values[1]) +"\n"+
        //        "Z value: "+ Float.toString(event.values[0]));
    }

    private double calculateLength(){
        ArrayList firstValue = valueArray.firstEntry().getValue();
        ArrayList lastValue = valueArray.lastEntry().getValue();
        Double startTime = valueArray.firstKey();
        Double endTime = valueArray.lastKey();
        time = (endTime-startTime)/1000;
        double distance = velocity * Math.cos(Math.toRadians(angle)) * time;
        //gyroText.setText(Double.toString(time));
        //gyroText.setText((Float)firstValue.get(0) + " " +(Float)firstValue.get(1));
        return distance;
    }


    public static double calcRotationAngleInDegrees(ArrayList centerPt, ArrayList targetPt)
    {
        // calculate the angle theta from the deltaY and deltaX values
        // (atan2 returns radians values from [-PI,PI])
        // 0 currently points EAST.
        // NOTE: By preserving Y and X param order to atan2,  we are expecting
        // a CLOCKWISE angle direction.
        double theta = Math.atan2((Float)targetPt.get(0) - (Float)centerPt.get(0), (Float)targetPt.get(1) - (Float)centerPt.get(1));

        // rotate the theta angle clockwise by 90 degrees
        // (this makes 0 point NORTH)
        // NOTE: adding to an angle rotates it clockwise.
        // subtracting would rotate it counter-clockwise
        theta += Math.PI/2.0;

        // convert from radians to degrees
        // this will give you an angle from [0->270],[-180,0]
        double angle = Math.toDegrees(theta);

        // convert to positive range [0-360)
        // since we want to prevent negative angles, adjust them now.
        // we can assume that atan2 will not return a negative value
        // greater than one partial rotation
        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
}

