package com.example.water_intake_time_master_function;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText goalEditText;
    private TextView currentIntakeTextView;
    private TextView goalAchievedTextView;
    private ImageView glassImage;
    private ProgressBar progressBar;
    private TextView progressText;
    private double currentIntake = 0.0;
    private double goal;
    private static final int[] IMAGE_IDS = {R.drawable.glass1, R.drawable.glass2, R.drawable.glass3, R.drawable.glass4}; // Image resource IDs
    private SharedPreferences sharedPreferences;
    private static final String PREF_CURRENT_INTAKE = "current_intake";
    private static final String PREF_GOAL = "goal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_intake);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        goalEditText = findViewById(R.id.editTextText);
        currentIntakeTextView = findViewById(R.id.textView3);
        goalAchievedTextView = findViewById(R.id.textView4);
        glassImage = findViewById(R.id.imageView3);
        progressBar = findViewById(R.id.progressBar2);
        progressText = findViewById(R.id.progressText);

        // Load saved values
        loadSavedValues();

        // Update current intake text and image on goal change
        goalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    goal = Double.parseDouble(s.toString());
                    updateCurrentIntakeText();
                    updateImage();
                    saveValues(); // Save the updated goal
                } catch (NumberFormatException e) {
                    // Handle invalid input (optional)
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Button click listeners for adding water intake
        Button button100ml = findViewById(R.id.button);
        button100ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIntake += 0.1;
                updateCurrentIntakeText();
                updateImage();
                updateProgressBar();
                checkGoalReached();
                saveValues(); // Save the updated intake
            }
        });

        Button button250ml = findViewById(R.id.button2);
        button250ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIntake += 0.25;
                updateCurrentIntakeText();
                updateImage();
                updateProgressBar();
                checkGoalReached();
                saveValues(); // Save the updated intake
            }
        });

        Button button500ml = findViewById(R.id.button3);
        button500ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIntake += 0.5;
                updateCurrentIntakeText();
                updateImage();
                updateProgressBar();
                checkGoalReached();
                saveValues(); // Save the updated intake
            }
        });

        Button button1000ml = findViewById(R.id.button4);
        button1000ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIntake += 1.0;
                updateCurrentIntakeText();
                updateImage();
                updateProgressBar();
                checkGoalReached();
                saveValues(); // Save the updated intake
            }
        });

        // Button click listener for GO button
    }

    private void loadSavedValues() {
        // Load current intake
        currentIntake = sharedPreferences.getFloat(PREF_CURRENT_INTAKE, 0.0f);
        // Load goal
        goal = sharedPreferences.getFloat(PREF_GOAL, 0.0f);
        // Update UI
        updateCurrentIntakeText();
        updateImage();
        updateProgressBar();
        checkGoalReached();
    }

    private void saveValues() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(PREF_CURRENT_INTAKE, (float) currentIntake);
        editor.putFloat(PREF_GOAL, (float) goal);
        editor.apply();
    }

    private void updateCurrentIntakeText() {
        String text = String.format("%.2f/%.1f Litres", currentIntake, goal);
        currentIntakeTextView.setText(text);
    }

    private void updateImage() {
        int imageIndex = (int) Math.floor((currentIntake / goal) * (IMAGE_IDS.length - 1)); // Calculate image index based on progress
        glassImage.setImageResource(IMAGE_IDS[imageIndex]);
    }

    private void updateProgressBar() {
        int progress = (int) Math.round((currentIntake / goal) * 100); // Calculate progress percentage and round it
        progressBar.setProgress(progress);
        progressText.setText(progress + "%"); // Update progress text
    }

    private void checkGoalReached() {
        if (currentIntake >= goal) {
            goalAchievedTextView.setVisibility(View.VISIBLE);
        } else {
            goalAchievedTextView.setVisibility(View.INVISIBLE);
        }
    }
}