package com.example.water_intake_time_master_function.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.water_intake_time_master_function.DrawingView;
import com.example.water_intake_time_master_function.R;

public class DrawingFragment extends Fragment {

    private DrawingView drawingView;
    private Button btnClear;
    private Button btnSave;
    private Spinner colorSpinner;
    private SeekBar sizeSeekBar;

    public DrawingFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_drawing_screen, container, false);

        drawingView = rootView.findViewById(R.id.drawingView);
        btnClear = rootView.findViewById(R.id.btn_clear);
        btnSave = rootView.findViewById(R.id.btn_save);
        colorSpinner = rootView.findViewById(R.id.colorSpinner);
        sizeSeekBar = rootView.findViewById(R.id.sizeSeekBar);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.clearDrawing();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.saveDrawing();
            }
        });

        // Set up Spinner for color selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedColor = parent.getItemAtPosition(position).toString();
                switch (selectedColor) {
                    case "Red":
                        drawingView.setDrawColor(Color.RED);
                        break;
                    case "Blue":
                        drawingView.setDrawColor(Color.BLUE);
                        break;
                    case "Black":
                        drawingView.setDrawColor(Color.BLACK);
                        break;
                    case "Yellow":
                        drawingView.setDrawColor(Color.YELLOW);
                        break;
                    case "Green":
                        drawingView.setDrawColor(Color.GREEN);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set up SeekBar listener to change brush size
        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float size = progress / 10.0f; // Adjust this scaling factor as needed
                drawingView.setBrushSize(size);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return rootView;
    }
}
