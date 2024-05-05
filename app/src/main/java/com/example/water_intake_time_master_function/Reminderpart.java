package com.example.water_intake_time_master_function;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Reminderpart extends AppCompatActivity {
    private static final String channelID = "notification_channel";
    private static final int notificationID = 1;
    private EditText titleEditText, messageEditText;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminderpart);

        titleEditText = findViewById(R.id.titleET);
        messageEditText = findViewById(R.id.messageET);
        submitButton = findViewById(R.id.submitButton);

        createNotificationChannel();

        LinearLayout linearLayout = findViewById(R.id.mainnnLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNotification();
            }
        });
    }

    private void scheduleNotification() {
        String title = titleEditText.getText().toString();
        String message = messageEditText.getText().toString();

        Intent intent = new Intent(Reminderpart.this, NotificationReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                Reminderpart.this,
                notificationID,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long timeInMillis = getTimeInMillis();
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
        );

        showAlert(timeInMillis, title, message);
    }

    private void showAlert(long time, String title, String message) {
        Date date = new Date(time);
        DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(Reminderpart.this);
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(Reminderpart.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(Reminderpart.this);
        builder.setTitle("Notification Scheduled")
                .setMessage("Title: " + title + "\nMessage: " + message + "\nAt: "
                        + dateFormat.format(date) + " " + timeFormat.format(date))
                .setPositiveButton("Okay", null)
                .show();
    }

    private long getTimeInMillis() {
        DatePicker datePicker = findViewById(R.id.datePicker);
        TimePicker timePicker = findViewById(R.id.timePicker);

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar.getTimeInMillis();
    }

    private void createNotificationChannel() {
        CharSequence name = "Notification Channel";
        String desc = "A Description of the Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel(channelID, name, importance);
        channel.setDescription(desc);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
