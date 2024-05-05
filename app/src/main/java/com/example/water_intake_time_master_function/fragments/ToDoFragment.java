package com.example.water_intake_time_master_function.fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.water_intake_time_master_function.R;

import java.util.HashSet;
import java.util.Set;

public class ToDoFragment extends Fragment {

    private LinearLayout tasksContainer;
    private EditText newTaskEditText;
    private int currentIcon;
    private SharedPreferences sharedPreferences;
    private static final String PREF_TASKS = "tasks";
    private static final String PREF_CURRENT_ICON = "current_icon";
    private static final String PREF_HINT_TEXT = "hint_text";

    public ToDoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_to_do, container, false);

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        // Initialize views
        tasksContainer = view.findViewById(R.id.tasksContainer);
        newTaskEditText = view.findViewById(R.id.newTaskEditText);

        // Load the current icon from SharedPreferences
        currentIcon = sharedPreferences.getInt(PREF_CURRENT_ICON, -1);
        // Load the hint text from SharedPreferences
        String hintText = sharedPreferences.getString(PREF_HINT_TEXT, "");
        newTaskEditText.setHint(hintText); // Set the hint text

        // Load saved tasks
        Set<String> tasks = sharedPreferences.getStringSet(PREF_TASKS, new HashSet<String>());
        for (String task : tasks) {
            addNewTask(task, currentIcon);
        }

        Button addTaskButton = view.findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = newTaskEditText.getText().toString().trim();
                if (!taskText.isEmpty()) {
                    addNewTask(taskText, currentIcon);
                    newTaskEditText.setText("");
                    saveTasks(); // Save tasks to SharedPreferences
                }
            }
        });

        Button workButton = view.findViewById(R.id.work);
        workButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTaskEditText.setHint("Enter Work Task");
                currentIcon = R.drawable.work;
                saveCurrentIconAndHint("Enter Work Task"); // Save the current icon and hint text to SharedPreferences
            }
        });

        Button sportButton = view.findViewById(R.id.sport);
        sportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTaskEditText.setHint("Enter Sport Task");
                currentIcon = R.drawable.sport;
                saveCurrentIconAndHint("Enter Sport Task"); // Save the current icon and hint text to SharedPreferences
            }
        });

        Button gymButton = view.findViewById(R.id.gym);
        gymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTaskEditText.setHint("Enter Gym Task");
                currentIcon = R.drawable.gym;
                saveCurrentIconAndHint("Enter Gym Task"); // Save the current icon and hint text to SharedPreferences
            }
        });

        Button otherButton = view.findViewById(R.id.other);
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTaskEditText.setHint("Enter Other Task");
                currentIcon = R.drawable.other;
                saveCurrentIconAndHint("Enter Other Task"); // Save the current icon and hint text to SharedPreferences
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Save tasks to SharedPreferences when fragment is paused
        saveTasks();
    }

    private void addNewTask(String taskText, int iconResource) {
        // Create a new LinearLayout for the task
        LinearLayout taskLayout = new LinearLayout(requireContext());
        taskLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        taskLayout.setOrientation(LinearLayout.HORIZONTAL);
        taskLayout.setPadding(16, 16, 16, 16);

        if (iconResource != -1) { // Check if icon resource is valid
            // Create ImageView for task icon
            ImageView iconView = new ImageView(requireContext());
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                    100, 100); // Set the desired icon size
            iconView.setLayoutParams(iconParams);
            iconView.setImageResource(iconResource); // Set the icon image resource
            taskLayout.addView(iconView);
        }

        // Create TextView for task description
        TextView taskTextView = new TextView(requireContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        taskTextView.setLayoutParams(params);
        taskTextView.setText(taskText);
        taskLayout.addView(taskTextView);

        // Create CheckBox for task completion
        CheckBox checkBox = new CheckBox(requireContext());
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        checkBoxParams.setMargins(16, 0, 16, 0);
        checkBox.setLayoutParams(checkBoxParams);
        taskLayout.addView(checkBox);

        // Create Delete Button
        Button deleteButton = new Button(requireContext());
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
                saveTasks(); // Save tasks to SharedPreferences after deletion
            }
        });
        taskLayout.addView(deleteButton);

        // Add the new task layout to the tasks container
        tasksContainer.addView(taskLayout);
    }

    private void saveTasks()
    {
        // Save the tasks to SharedPreferences
        Set<String> tasks = new HashSet<>();
        for (int i = 0; i < tasksContainer.getChildCount(); i++)
        {
            View child = tasksContainer.getChildAt(i);
            if (child instanceof LinearLayout) {
                LinearLayout taskLayout = (LinearLayout) child;
                TextView taskTextView = (TextView) taskLayout.getChildAt(1);
                String taskText = taskTextView.getText().toString();
                tasks.add(taskText);
            }
        }
        sharedPreferences.edit().putStringSet(PREF_TASKS, tasks).apply();
    }

    private void saveCurrentIconAndHint(String hint) {
        // Save the current icon and hint text to SharedPreferences
        sharedPreferences.edit().putInt(PREF_CURRENT_ICON, currentIcon).apply();
        sharedPreferences.edit().putString(PREF_HINT_TEXT, hint).apply();
    }
}