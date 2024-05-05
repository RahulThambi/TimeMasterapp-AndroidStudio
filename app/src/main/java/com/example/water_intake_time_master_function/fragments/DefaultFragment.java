package com.example.water_intake_time_master_function.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.water_intake_time_master_function.Default;
import com.example.water_intake_time_master_function.R;
import com.example.water_intake_time_master_function.Reminderpart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DefaultFragment extends Fragment
{

    TextView textView6, textView5, textView7;
    ImageView imageView8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_default, container, false);

        textView6 = view.findViewById(R.id.textView6);
        textView5 = view.findViewById(R.id.textView5);

        //time and date - 1)
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        textView6.setText(currentDate);
        //time and date - 1)

        //greetings+username - 2)
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("EXTRA_USERNAME")) {
            String userName = bundle.getString("EXTRA_USERNAME");
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour >= 6 && hour < 12) {
                textView5.setText("Good morning!, " + userName);
            } else if (hour >= 12 && hour < 18) {
                textView5.setText("Good afternoon!, " + userName);
            } else {
                textView5.setText("Good evening!, " + userName);
            }
        }
        //greetings+username - 2)

        return view;
    }

}
