package com.example.water_intake_time_master_function;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class DrawingScreen extends AppCompatActivity
{
    private DrawingView drawingView;
    private Button btnClear;
    private Button btnSave;
    private Spinner colorSpinner;
    private SeekBar sizeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_screen);

        drawingView = findViewById(R.id.drawingView);
        btnClear = findViewById(R.id.btn_clear);
        btnSave = findViewById(R.id.btn_save);
        colorSpinner = findViewById(R.id.colorSpinner);
        sizeSeekBar = findViewById(R.id.sizeSeekBar);

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
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
    }
}
