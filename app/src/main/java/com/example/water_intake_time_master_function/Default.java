package com.example.water_intake_time_master_function;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Default extends AppCompatActivity
{
    TextView textView6, textView5;
    ImageView imageView8;
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        textView6 = findViewById(R.id.textView6);
        textView5 = findViewById(R.id.textView5);
        button5 = findViewById(R.id.button5);

        //time and date - 1)
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        textView6.setText(currentDate);
        //time and date - 1)

        //greetings+username - 2)
        Intent intent = getIntent();
        String userName = intent.getStringExtra("EXTRA_USERNAME");
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 12)
        {
            textView5.setText("Good morning!, " + userName);
        } else if (hour >= 12 && hour < 18)
        {
            textView5.setText("Good afternoon!, " + userName);
        } else {
            textView5.setText("Good evening!, " + userName);
        }
        //greetings+username - 2)
    }
    public void oncStart(View view)
    {
        Intent intent = new Intent(Default.this,MainActivity2.class);
        startActivity(intent);
    }
}

