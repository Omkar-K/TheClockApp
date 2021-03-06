
package com.example.theclockapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity  {

    private static TextView countdownTimerText;
    private static EditText minutes,seconds;
    private static Button startTimer, resetTimer;
    private static CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        countdownTimerText = (TextView) findViewById(R.id.countdownText);
        minutes = (EditText) findViewById(R.id.enterMinutes);
        seconds = (EditText) findViewById(R.id.enterSeconds);
        startTimer = (Button) findViewById(R.id.startTimer);
        resetTimer = (Button) findViewById(R.id.resetTimer);

        startTimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (countDownTimer == null) {
                    String getMinutes = minutes.getText().toString();//Get minutes from edittext
                    String getSec = seconds.getText().toString();
                    //Check if edit text is empty
                    if (!getMinutes.equals("") && getMinutes.length() > 0) {
                        //Convert the min into milliseconds
                        int minsToMili = Integer.parseInt(getMinutes) * 60 * 1000;
                        if (!getSec.equals("") && getSec.length() > 0) {
                            //Convert the seconds into milliseconds
                            int secsToMili = Integer.parseInt(getSec) * 1000;

                            int totalmiliSecs = minsToMili+secsToMili;
                            startTimer(totalmiliSecs);//start countdown
                            startTimer.setText(getString(R.string.stop_timer));
                            startTimer.setBackgroundColor(Color.parseColor( "#E64A19" ));
                        }else{
                            startTimer.setBackgroundColor(Color.parseColor( "#E64A19" ));
                            startTimer(minsToMili);//start countdown
                            startTimer.setText(getString(R.string.stop_timer)) ;
                        }


                    }else if (!getSec.equals("") && getSec.length() > 0){
                        //Convert the seconds into milliseconds
                        int secsToMili = Integer.parseInt(getSec) * 1000;

                        startTimer(secsToMili);//start countdown
                        startTimer.setText(getString(R.string.stop_timer));
                        startTimer.setBackgroundColor(Color.parseColor( "#E64A19" ));
                    } else
                        Toast.makeText(Timer.this, "Enter some values to start", Toast.LENGTH_SHORT).show();
                    //Display toast if edittext is empty
                } else {
                    //Else stop timer and change text
                    stopCountdown();
                    startTimer.setText(getString(R.string.start_timer));
                }
                //hiding the keyboard after timer starts
                hideKeyboard((Button)v);
            }


        });
        //Action for reset button
        resetTimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                stopCountdown();//stop count down
                minutes.setText("");
                seconds.setText("");
                //Change text to Start Timer
                startTimer.setText(getString(R.string.start_timer));
                startTimer.setBackgroundColor(Color.GREEN);
                countdownTimerText.setText(getString(R.string.timer));//Change Timer text

            }
        });


    }

    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            startTimer.setText(getString(R.string.start_timer));
            startTimer.setBackgroundColor(Color.GREEN);
            countDownTimer = null;
        }
    }

    //Start Countdown method
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }

            public void onFinish() {

                countdownTimerText.setText("Timer is up!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                startTimer.setText(getString(R.string.start_timer));//Change button text
                startTimer.setBackgroundColor(Color.GREEN);
            }
        }.start();

    }

    //Method for hiding the keyboard
    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }


}

