package com.codeway_task.alarm_clock_app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Calendar;


public class AlarmNotificationActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_notification);

        String message = getIntent().getStringExtra("message");
        TextView alarmMessageTextView = findViewById(R.id.alarmMessageTextView);
        alarmMessageTextView.setText(message);

        // Set the image resource in the ImageView
        ImageView alertImageView = findViewById(R.id.imageView);
        Glide.with(this)
                .asGif()
                .load(R.drawable.alert) // Change the resource as needed
                .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable caching for GIFs
                .into(alertImageView);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            // Handle case where AlarmManager is null
            Toast.makeText(this, "AlarmManager is not available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Button dismissButton = findViewById(R.id.dismissButton);
        Button snoozeButton = findViewById(R.id.snoozeButton);

        // Initialize the MediaPlayer with the alarm sound
        mediaPlayer = MediaPlayer.create(this, R.raw.a_old_telephone);

        // Play the alarm sound
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        // Set up onClickListener for Dismiss button
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop the alarm sound
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                // Close the activity
                cancelSnoozeAlarm();
                finish();
            }
        });

        // Set up onClickListener for Snooze button
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop the alarm sound
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                // Implement snooze logic here, such as setting a new alarm for a certain duration
                // and finishing the activity
                snoozeAlarm(5);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer resources when the activity is destroyed
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void snoozeAlarm(int minutes) {
        // Get current time
        Calendar currentTime = Calendar.getInstance();
        // Calculate snooze time (current time + snooze duration)
        currentTime.add(Calendar.MINUTE, minutes);
        // Create intent for the alarm receiver
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction("SNOOZE_ACTION");
        // Create pending intent
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Set the alarm to snooze for specified minutes
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime.getTimeInMillis(), pendingIntent);
        // Show toast indicating snooze time
        Toast.makeText(this, "Alarm snoozed for " + minutes + " minutes", Toast.LENGTH_SHORT).show();
    }

    private void cancelSnoozeAlarm() {
        // Cancel the pending intent if it's not null
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}