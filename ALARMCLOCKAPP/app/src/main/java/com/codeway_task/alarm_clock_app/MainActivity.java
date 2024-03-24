package com.codeway_task.alarm_clock_app;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String ALARM_PREFS = "alarm_prefs";
    private static final String ALARM_HOUR_KEY = "alarm_hour";
    private static final String ALARM_MINUTE_KEY = "alarm_minute";
    private List<Alarm> alarms;
    private RecyclerView recyclerView;
    private AlarmAdapter adapter;
    private TimePicker timePicker;
    TextView currentTimeTextView;
    Button setAlarmButton;
    private AlarmManager alarmManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTimeTextView=findViewById(R.id.currentTimeTextView);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        timePicker = findViewById(R.id.timePicker);
        recyclerView = findViewById(R.id.recyclerView);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy HH:mm", Locale.getDefault());
        String currentTime = dateFormat.format(calendar.getTime());
        currentTimeTextView.setText(currentTime);

//        alarms = new ArrayList<>();
        sharedPreferences = getSharedPreferences(ALARM_PREFS, Context.MODE_PRIVATE);
        alarms = loadAlarms();
        adapter = new AlarmAdapter(alarms,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Calendar alarmTime = Calendar.getInstance();
                alarmTime.set(Calendar.HOUR_OF_DAY, hour);
                alarmTime.set(Calendar.MINUTE, minute);
                alarmTime.set(Calendar.SECOND, 0);

                // Check if the alarm time is in the past
                if (alarmTime.before(Calendar.getInstance())) {
                    alarmTime.add(Calendar.DAY_OF_MONTH, 1); // Set the alarm for tomorrow if it's in the past
                }

                // Create an intent to be fired when the alarm goes off
                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                int requestCode = (int) System.currentTimeMillis();
                @SuppressLint("UnspecifiedImmutableFlag")
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode, intent, 0);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // Set the alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);

                addAlarm(hour,minute);
                saveAlarms(); // Save the alarms to SharedPreferences
                Toast.makeText(MainActivity.this, "Alarm set for " + hour + ":" + minute, Toast.LENGTH_SHORT).show();

            }
        });
    }

//    private void addAlarm(int hour, int minute) {
//        Alarm alarm = new Alarm(hour, minute);
//        alarms.add(alarm);
//        adapter.notifyDataSetChanged();
//    }
    private void addAlarm(int hour, int minute) {
        if (alarms == null) {
            alarms = new ArrayList<>();
        }
        alarms.add(new Alarm(hour, minute));
        adapter.notifyDataSetChanged(); // Update RecyclerView
    }
    private void saveAlarms() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear existing alarms
        Set<String> alarmSet = new ArraySet<>();
        for (Alarm alarm : alarms) {
            alarmSet.add(alarm.getHour() + ":" + alarm.getMinute());
        }
        editor.putStringSet(ALARM_PREFS, alarmSet);
        editor.apply();
    }

    private List<Alarm> loadAlarms() {
        List<Alarm> loadedAlarms = new ArrayList<>();
        Set<String> alarmSet = sharedPreferences.getStringSet(ALARM_PREFS, null);
        if (alarmSet != null) {
            for (String alarmTime : alarmSet) {
                String[] timeComponents = alarmTime.split(":");
                int hour = Integer.parseInt(timeComponents[0]);
                int minute = Integer.parseInt(timeComponents[1]);
                loadedAlarms.add(new Alarm(hour, minute));
            }
        }
        return loadedAlarms;
    }


    public void deleteAlarm(int position) {
        adapter.deleteAlarm(position);
        saveAlarms();
    }
}