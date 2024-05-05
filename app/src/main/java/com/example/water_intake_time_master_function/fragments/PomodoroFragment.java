package com.example.water_intake_time_master_function.fragments;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.water_intake_time_master_function.R;

public class PomodoroFragment extends Fragment {

    private NumberPicker hoursPicker;
    private ImageView timerImageView;
    private NumberPicker minutesPicker;
    private Button startTimerButton;
    private TextView timerTextView;
    private CountDownTimer pomodoroTimer;
    private boolean isTimerRunning = false;
    private final int PHASES = 4;
    private int currentPhase = 1;
    private int totalTimeInMinutes;
    private int workTimePerPhaseInMinutes;
    private int breakTimePerPhaseInMinutes;

    public PomodoroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pomodoro_timer, container, false);
        hoursPicker = view.findViewById(R.id.hoursPicker);
        minutesPicker = view.findViewById(R.id.minutesPicker);
        startTimerButton = view.findViewById(R.id.startTimerButton);
        timerTextView = view.findViewById(R.id.timerTextVIew);

        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(24);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(59);

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

        return view;
    }

    private void calculateTimerIntervals() {
        workTimePerPhaseInMinutes = totalTimeInMinutes / (PHASES * 2); // Calculate work time per phase
        breakTimePerPhaseInMinutes = workTimePerPhaseInMinutes / 4; // Calculate break time per phase
    }

    private void startPomodoroTimer() {
        long totalTimeInMilliseconds = (workTimePerPhaseInMinutes + breakTimePerPhaseInMinutes) * 60 * 1000; // Total time per phase

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
        // updateTimerImage(); // Update timer image after starting the timer
    }


//    private void updateTimerImage() {
//        int imageResource;
//        if (currentPhase % 2 == 1) { // Work phase
//            imageResource = getResources().getIdentifier("tree" + (currentPhase / 2) + ".png", "drawable", requireContext().getPackageName());
//        } else { // Break phase
//            imageResource = getResources().getIdentifier("rest", "drawable", requireContext().getPackageName());
//        }
//        timerImageView.setImageResource(imageResource); // Set the timer image
//
//        // Debug statement
//        Log.d("TimerImageDebug", "Image Resource ID: " + imageResource);
//    }


    private void updateTimerText(long secondsRemaining) {
        long minutes = secondsRemaining / 60;
        long seconds = secondsRemaining % 60;
        String timerText = String.format("%s: %02d:%02d", (currentPhase % 2 == 1) ? "Work" : "Break", minutes, seconds);
        timerTextView.setText(timerText);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (pomodoroTimer != null)
        {
            pomodoroTimer.cancel();
        }
    }
}