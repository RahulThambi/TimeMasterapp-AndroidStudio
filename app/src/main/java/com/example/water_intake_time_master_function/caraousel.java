package com.example.water_intake_time_master_function;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class caraousel extends AppCompatActivity {


    ViewPager2 viewPager;
    Button btnGo,smallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caraousel);

        viewPager = findViewById(R.id.viewPager);
        btnGo = findViewById(R.id.btnGo);
        smallButton = findViewById(R.id.smallButton);

        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        btnGo.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, LOGIN.class);
            startActivity(intent);

        });
        smallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(caraousel.this, Reminderpart.class);
                startActivity(intent);
            }
        });
    }
}