package com.example.water_intake_time_master_function;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;

import android.os.CountDownTimer;

import android.view.View;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.NumberPicker;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pomodoro_Timer extends AppCompatActivity {

    private NumberPicker hoursPicker;

    private ImageView timerImageView;

    private NumberPicker minutesPicker;

    private Button startTimerButton;

    private TextView timerTextView;

    private CountDownTimer pomodoroTimer;

    private boolean isTimerRunning = false;

    private final int PHASES = 4; // Number of phases

    private int currentPhase = 1; // Current phase

    private int totalTimeInMinutes; // Total time entered in minutes
    private int workTimePerPhaseInMinutes; // Work time per phase in minutes
    private int breakTimePerPhaseInMinutes; // Break time per phase in minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_timer);

        hoursPicker = findViewById(R.id.hoursPicker);
        minutesPicker = findViewById(R.id.minutesPicker);
        startTimerButton = findViewById(R.id.startTimerButton);
        timerTextView = findViewById(R.id.timerTextVIew);
       // timerImageView = findViewById(R.id.imageView2); // Update the ID if it's different

        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(24);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(59);

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ... existing code ...
                updateTimerImage(); // Call updateTimerImage() after starting the timer
            }
        });

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {
                    int selectedHours = hoursPicker.getValue();
                    int selectedMinutes = minutesPicker.getValue();
                    totalTimeInMinutes = (selectedHours * 60) + selectedMinutes; // Convert hours to minutes
                    calculateTimerIntervals();
                    startPomodoroTimer();
                    startTimerButton.setText("Give Up!");
                } else {
                    if (pomodoroTimer != null) {
                        pomodoroTimer.cancel();
                        timerTextView.setText("Timer Stopped");
                        startTimerButton.setText("Start Timer");
                        isTimerRunning = false;
                    }
                }
            }
        });
    }
    private void updateTimerImage() {
        int imageResource;
        if (currentPhase % 2 == 1) { // Work phase
            imageResource = getResources().getIdentifier("tree" + (currentPhase / 2) + ".png", "drawable", getPackageName());
        } else { // Break phase
            imageResource = getResources().getIdentifier("rest", "drawable", getPackageName());
        }
        timerImageView.setImageResource(imageResource);
    }

    private void calculateTimerIntervals() {
        workTimePerPhaseInMinutes = totalTimeInMinutes / (PHASES * 2); // Calculate work time per phase
        breakTimePerPhaseInMinutes = workTimePerPhaseInMinutes / 4; // Calculate break time per phase
    }

    private void startPomodoroTimer() {
        long totalTimeInMilliseconds;
        String timerText;

        totalTimeInMilliseconds = (workTimePerPhaseInMinutes + breakTimePerPhaseInMinutes) * 60 * 1000; // Divide total time per phase

        if (currentPhase % 2 == 1) { // Work phase
            timerText = String.format("Work: %02d:%02d", workTimePerPhaseInMinutes, 0);
        } else { // Break phase
            timerText = String.format("Break: %02d:%02d", breakTimePerPhaseInMinutes, 0);
        }
        pomodoroTimer = new CountDownTimer(totalTimeInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimerText(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                currentPhase++;
                if (currentPhase > PHASES) {
                    timerTextView.setText("Pomodoro Finished!");
                    isTimerRunning = false;
                    startTimerButton.setText("Start Timer");
                } else {
                    startPomodoroTimer();
                }
            }
        };
        pomodoroTimer.start();
        isTimerRunning = true;
        timerTextView.setText(timerText);
    }

    private void updateTimerText(long secondsRemaining) {
        long minutes = secondsRemaining / 60;
        long seconds = secondsRemaining % 60;
        String timerText = String.format("%s: %02d:%02d", (currentPhase % 2 == 1) ? "Work" : "Break", minutes, seconds);
        timerTextView.setText(timerText);
    }

    protected void onStop() {
        super.onStop();
        if (pomodoroTimer != null) {
            pomodoroTimer.cancel();
        }
    }
}

