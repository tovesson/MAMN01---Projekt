package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ThrowActivity extends AppCompatActivity implements SensorEventListener
{
    private TextView gyroText;
    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];
    private boolean buttonDown = false;
    private ArrayList<float[]> accValues;
    private float xAcc;
    private float yAcc;
    private float zAcc;
    private float totAcc;
    private float angle;
    private double velocity;
    private double yVelocity;
    private TextView throwImageText;
    private Button startThrowGameButton;
    private Button throwButtonDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw_start_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startThrowGameButton = findViewById(R.id.startThrowGameButton);
        final TextView throwMasterTitle = findViewById(R.id.throwMasterTitle);
        final TextView throwMasterSubtitle = findViewById(R.id.throwMasterSubtitle);
        final ImageView throwImage = findViewById(R.id.imageView2);
        throwImageText = findViewById(R.id.textView6);
        SensorManager mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //Register listener and set sensor_delay to 100ms
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL / 100);
        throwButtonDone = findViewById(R.id.throwButtonDone);
        accValues = new ArrayList<float[]>();

        startThrowGameButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    accValues.clear();
                    xAcc = 0;
                    yAcc = 0;
                    zAcc = 0;
                    buttonDown = true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonDown = false;
                    calcAngle(accValues);
                }
                return true;
            }
        });

        throwButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        new CountDownTimer((2) * 1000, 1000) // Wait 5 secs, tick every 1 sec
        {
            @Override
            public final void onTick(final long millisUntilFinished) {
                throwButtonDone.setVisibility(View.INVISIBLE);
                startThrowGameButton.setVisibility(View.INVISIBLE);
                throwMasterSubtitle.setVisibility(View.INVISIBLE);
                throwImageText.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFinish() {
                startThrowGameButton.setVisibility(View.VISIBLE);
                throwMasterSubtitle.setVisibility(View.VISIBLE);
                throwImageText.setVisibility(View.VISIBLE);

            }

        }.start();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate

        final float alpha = 0.8f;
        if(buttonDown) {
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            //With low pass filtering
            //linear_acceleration[0] = event.values[0] - gravity[0];
            //linear_acceleration[1] = event.values[1] - gravity[1];
            //linear_acceleration[2] = event.values[2] - gravity[2];

            //Without low pass filtering
            linear_acceleration[0] = event.values[0];
            linear_acceleration[1] = event.values[1];
            linear_acceleration[2] = event.values[2];
            accValues.add(linear_acceleration);
        }
    }

    public void calcAngle(ArrayList<float[]> values){
        for(int i = 0; i < values.size(); i++){
            xAcc += values.get(i)[0];
            yAcc += values.get(i)[1];
            zAcc += values.get(i)[2];
        }
        //Should be divided by values.size() to get the average acceleration, but values will be to low.
        xAcc = Math.abs(xAcc);
        yAcc = Math.abs(yAcc);
        zAcc = Math.abs(zAcc);
        totAcc = xAcc + yAcc ;
        //calculate the angle as a percentage of the acceleration in x compared to the total (in x and y direction) acceleration.
        angle = 90 - Math.abs(((xAcc / totAcc) * 90));
        double throwTime = (values.size()*1.0)/100;
        if(throwTime > 0.3) {
            velocity = (1.2 / throwTime )+ (xAcc / values.size() )* throwTime;
            yVelocity = velocity * Math.sin(Math.toRadians(angle));
            double maxHeightTime = yVelocity / 9.82;
            double throwHeight = 1.7;
            double maxHeight = yVelocity * maxHeightTime - (0.5 * 9.82 * Math.pow(maxHeightTime, 2));
            double totHeight = throwHeight + maxHeight;
            double totAirTime = Math.sqrt(totHeight * 2 / 9.82) + maxHeightTime;
            double distance = velocity * Math.cos(Math.toRadians(angle)) * totAirTime;
            throwImageText.setTextSize(25);
            throwImageText.setText("Distance thrown" + "\n" + String.format("%.2f", distance) + "meter");
            startThrowGameButton.setVisibility(View.INVISIBLE);
            throwButtonDone.setVisibility(View.VISIBLE);
        }else{
            throwImageText.setTextSize(25);
            throwImageText.setText("Hold the button longer during the throw");
            startThrowGameButton.setVisibility(View.VISIBLE);
            throwButtonDone.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

