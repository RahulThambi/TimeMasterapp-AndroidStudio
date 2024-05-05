package com.example.water_intake_time_master_function.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.water_intake_time_master_function.R;

public class WaterFragment extends Fragment {

    private EditText goalEditText;
    private TextView currentIntakeTextView;
    private TextView goalAchievedTextView;
    private ImageView glassImage;
    private ProgressBar progressBar;
    private TextView progressText;
    private double currentIntake = 0.0;
    private double goal;
    private static final int[] IMAGE_IDS = {R.drawable.glass1, R.drawable.glass2, R.drawable.glass3, R.drawable.glass4};
    private SharedPreferences sharedPreferences;
    private static final String PREF_GOAL = "goal";
    private static final String PREF_CURRENT_INTAKE = "current_intake";

    public WaterFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.water_intake, container, false);

        sharedPreferences = requireActivity().getSharedPreferences("WaterIntakePrefs", Context.MODE_PRIVATE);

        goalEditText = view.findViewById(R.id.editTextText);
        currentIntakeTextView = view.findViewById(R.id.textView3);
        goalAchievedTextView = view.findViewById(R.id.textView4);
        glassImage = view.findViewById(R.id.imageView3);
        progressBar = view.findViewById(R.id.progressBar2);
        progressText = view.findViewById(R.id.progressText);

        loadSavedData();

        goalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    goal = Double.parseDouble(s.toString());
                    updateCurrentIntakeText();
                    updateImage();
                    saveData();
                } catch (NumberFormatException e) {
                    // Handle invalid input
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        Button button100ml = view.findViewById(R.id.button);
        button100ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIntake += 0.1;
                updateCurrentIntakeText();
                updateImage();
                updateProgressBar();
                checkGoalReached();
                saveData();
            }
        });

        Button button250ml = view.findViewById(R.id.button2);
        button250ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIntake += 0.25;
                updateCurrentIntakeText();
                updateImage();
                updateProgressBar();
                checkGoalReached();
                saveData();
            }
        });

        Button button500ml = view.findViewById(R.id.button3);
        button500ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIntake += 0.5;
                updateCurrentIntakeText();
                updateImage();
                updateProgressBar();
                checkGoalReached();
                saveData();
            }
        });

        Button button1000ml = view.findViewById(R.id.button4);
        button1000ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIntake += 1.0;
                updateCurrentIntakeText();
                updateImage();
                updateProgressBar();
                checkGoalReached();
                saveData();
            }
        });

        return view;
    }

    private void loadSavedData() {
        goal = sharedPreferences.getFloat(PREF_GOAL, 0.0f);
        currentIntake = sharedPreferences.getFloat(PREF_CURRENT_INTAKE, 0.0f);
        updateCurrentIntakeText();
        updateImage();
        updateProgressBar();
        checkGoalReached();
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(PREF_GOAL, (float) goal);
        editor.putFloat(PREF_CURRENT_INTAKE, (float) currentIntake);
        editor.apply();
    }

    private void updateCurrentIntakeText() {
        String text = String.format("%.2f/%.1f Litres", currentIntake, goal);
        currentIntakeTextView.setText(text);
    }

    private void updateImage() {
        int imageIndex = (int) Math.floor((currentIntake / goal) * (IMAGE_IDS.length - 1));
        glassImage.setImageResource(IMAGE_IDS[imageIndex]);
    }

    private void updateProgressBar() {
        int progress = (int) Math.round((currentIntake / goal) * 100);
        progressBar.setProgress(progress);
        progressText.setText(progress + "%");
    }

    private void checkGoalReached() {
        if (currentIntake >= goal) {
            goalAchievedTextView.setVisibility(View.VISIBLE);
        } else {
            goalAchievedTextView.setVisibility(View.INVISIBLE);
        }
    }
}
