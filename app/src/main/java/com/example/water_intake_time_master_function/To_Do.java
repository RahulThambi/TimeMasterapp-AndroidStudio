package com.example.water_intake_time_master_function;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.water_intake_time_master_function.fragments.DrawingFragment;

public class To_Do extends AppCompatActivity {

    private LinearLayout tasksContainer;
    private EditText newTaskEditText;
    private int currentIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        // Initialize views
        tasksContainer = findViewById(R.id.tasksContainer);
        newTaskEditText = findViewById(R.id.newTaskEditText);
    }

    // Method to handle Add Task button click
    public void onAddTaskClick(View view) {
        String taskText = newTaskEditText.getText().toString().trim();
        if (!taskText.isEmpty()) {
            addNewTask(taskText, currentIcon);
            newTaskEditText.setText("");
        }
    }

    // Method to add a new task
    private void addNewTask(String taskText, int iconResource) {
        // Create a new LinearLayout for the task
        LinearLayout taskLayout = new LinearLayout(this);
        taskLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        taskLayout.setOrientation(LinearLayout.HORIZONTAL);
        taskLayout.setPadding(16, 16, 16, 16);

        if (iconResource != -1) { // Check if icon resource is valid
            // Create ImageView for task icon
            ImageView iconView = new ImageView(this);
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                    100, 100); // Set the desired icon size
            iconView.setLayoutParams(iconParams);
            iconView.setImageResource(iconResource); // Set the icon image resource
            taskLayout.addView(iconView);
        }

        // Create TextView for task description
        TextView taskTextView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        taskTextView.setLayoutParams(params);
        taskTextView.setText(taskText);
        taskLayout.addView(taskTextView);

        // Create CheckBox for task completion
        CheckBox checkBox = new CheckBox(this);
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        checkBoxParams.setMargins(16, 0, 16, 0);
        checkBox.setLayoutParams(checkBoxParams);
        taskLayout.addView(checkBox);

        // Create Delete Button
        Button deleteButton = new Button(this);
        LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        deleteButton.setLayoutParams(deleteButtonParams);
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the task layout when Delete button is clicked
                tasksContainer.removeView(taskLayout);
            }
        });
        taskLayout.addView(deleteButton);

        // Add the new task layout to the tasks container
        tasksContainer.addView(taskLayout);
    }

    // Method to handle Work button click
    public void onWorkClick(View view) {
        newTaskEditText.setHint("Enter Work Task");
        currentIcon = R.drawable.work;
    }

    // Method to handle Sport button click
    public void onSportClick(View view) {
        newTaskEditText.setHint("Enter Sport Task");
        currentIcon = R.drawable.sport;
    }

    // Method to handle Gym button click
    public void onGymClick(View view) {
        newTaskEditText.setHint("Enter Gym Task");
        currentIcon = R.drawable.gym;
    }

    // Method to handle Other button click
    public void onOtherClick(View view) {
        newTaskEditText.setHint("Enter Other Task");
        currentIcon = R.drawable.other;
    }
    public void onPaintClick(View view)
    {
        DrawingFragment drawingFragment = new DrawingFragment();
        // Get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Begin a transaction to replace the current fragment with DrawingFragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_wrapper, drawingFragment);
        fragmentTransaction.addToBackStack(null); // Optional: add the transaction to the back stack
        fragmentTransaction.commit();
    }
}