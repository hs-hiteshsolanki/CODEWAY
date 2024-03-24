package com.codeway_task.alarm_clock_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");

        // Show a toast message to indicate that the alarm has triggered
        Toast.makeText(context, "Alarm Triggered: " + message, Toast.LENGTH_SHORT).show();

        // Implement any additional notification logic here, such as playing a sound
        // For example, you can play a sound using MediaPlayer
//        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.notification);
//        mediaPlayer.start();

        // Launch AlarmNotificationActivity when the alarm goes off
        Intent notificationIntent = new Intent(context, AlarmNotificationActivity.class);
        notificationIntent.putExtra("message", message);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(notificationIntent);
    }
}