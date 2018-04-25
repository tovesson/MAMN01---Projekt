package com.example.motionmasters.motionmasters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BlowActivity extends AppCompatActivity {
    private static final int sampleRate = 8000;
    private AudioRecord audio;
    private int bufferSize;
    private double lastLevel = 0;
    private Thread thread;
    private static final int SAMPLE_DELAY = 100;
    private ImageView mouthImage;
    private double result = 0;
    private double nextScreenCounter = 0;
    TextView resultText;
    private double startTime;
    private double currTime;
    private DecimalFormat decimalFormat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blow);
        mouthImage = (ImageView)findViewById(R.id.imageView);
        mouthImage.setKeepScreenOn(true);
        resultText = (TextView) findViewById(R.id.resultText);
        result = 0;
        nextScreenCounter = 0;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        decimalFormat = new DecimalFormat("0.00");

        try {
            bufferSize = AudioRecord
                    .getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO,
                            AudioFormat.ENCODING_PCM_16BIT);
        } catch (Exception e) {
            android.util.Log.e("TrackingFlow", "Exception", e);
        }

    }

    protected void onResume() {
        super.onResume();
        audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        audio.startRecording();
        thread = new Thread(new Runnable() {
            public void run() {
                while(thread != null && !thread.isInterrupted()){
                    //Let's make the thread sleep for a the approximate sampling time
                    try{Thread.sleep(SAMPLE_DELAY);}catch(InterruptedException ie){ie.printStackTrace();}
                    readAudioBuffer();//After this call we can get the last value assigned to the lastLevel variable

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(nextScreenCounter > 2.0){
                                //Intent myIntent = new Intent(BlowActivity.this, MainActivity.class);
                                //BlowActivity.this.startActivity(myIntent);
                            }
                            else if(lastLevel > 0 && lastLevel <= 50){
                                mouthImage.setImageResource(R.drawable.gradientone);
                                nextScreenCounter += 0.1;
                            }else if(lastLevel > 50 && lastLevel <= 200 ){
                                currTime = System.currentTimeMillis();
                                resultText.setText(decimalFormat.format((currTime-startTime)/1000));
                                mouthImage.setImageResource(R.drawable.gradienttwo);
                                //result += 0.1;
                                //resultText.setText(String.format("%.2f", result));
                            }
                            else if(lastLevel > 200 && lastLevel <= 300 ){
                                currTime = System.currentTimeMillis();
                                resultText.setText(decimalFormat.format((currTime-startTime)/1000));
                                mouthImage.setImageResource(R.drawable.gradientthree);
                                //result += 0.1;
                                //resultText.setText(String.format("%.2f", result));
                            }
                            else if(lastLevel > 300 && lastLevel <= 400 ){
                                currTime = System.currentTimeMillis();
                                resultText.setText(decimalFormat.format((currTime-startTime)/1000));
                                mouthImage.setImageResource(R.drawable.gradientfour);
                                //result += 0.1;
                                //resultText.setText(String.format("%.2f", result));
                            }
                            else if(lastLevel > 400 && lastLevel <= 500 ){
                                currTime = System.currentTimeMillis();
                                resultText.setText(decimalFormat.format((currTime-startTime)/1000));
                                mouthImage.setImageResource(R.drawable.gradientfive);
                                //result += 0.1;
                                //resultText.setText(String.format("%.2f", result));
                            }
                            else if(lastLevel > 500 ){
                                currTime = System.currentTimeMillis();
                                resultText.setText(decimalFormat.format((currTime-startTime)/1000));
                                mouthImage.setImageResource(R.drawable.gradientsix);
                                //result += 0.1;
                                //resultText.setText(String.format("%.2f", result));
                            }
                        }
                    });
                }
            }
        });
        thread.start();
    }

    /**
     * Functionality that gets the sound level out of the sample
     */
    private void readAudioBuffer() {

        try {
            short[] buffer = new short[bufferSize];

            int bufferReadResult = 1;

            if (audio != null) {

                // Sense the voice...
                bufferReadResult = audio.read(buffer, 0, bufferSize);
                double sumLevel = 0;
                for (int i = 0; i < bufferReadResult; i++) {
                    sumLevel += buffer[i];
                }
                lastLevel = Math.abs((sumLevel / bufferReadResult));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        thread.interrupt();
        thread = null;
        try {
            if (audio != null) {
                audio.stop();
                audio.release();
                audio = null;
            }
        } catch (Exception e) {e.printStackTrace();}
        Intent myIntent = new Intent(BlowActivity.this, MainActivity.class);
        BlowActivity.this.startActivity(myIntent);
    }
}