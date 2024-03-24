package com.codeway_task.alarm_clock_app;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private List<Alarm> alarms;
    Context context;
    AlarmAdapter(List<Alarm> alarms , Context context) {
        this.alarms = alarms;
        this.context = context;
    }

    @NonNull
    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new AlarmAdapter.AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.AlarmViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Alarm alarm = alarms.get(position);
        holder.hourTextView.setText(String.valueOf(alarm.hour));
        holder.minuteTextView.setText(String.valueOf(alarm.minute));
        holder.alarmSwitch.setChecked(alarm.isEnabled);
        holder.alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarm.isEnabled = isChecked;
                // Handle alarm enable/disable action here
                if (isChecked) {
                    // Enable alarm
                    startAlarm(context,alarm);
                } else {
                    // Disable alarm
                    stopAlarm(context,alarm);
                    Toast.makeText(context, "Stop Alarm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) v.getContext()).deleteAlarm(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView hourTextView;
        TextView minuteTextView;
        Switch alarmSwitch;
        ImageButton delete;
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            hourTextView = itemView.findViewById(R.id.hourTextView);
            minuteTextView = itemView.findViewById(R.id.minuteTextView);
            alarmSwitch = itemView.findViewById(R.id.alarmSwitch);
            delete=itemView.findViewById(R.id.item_alarm_recurring_delete);
        }
    }
    public void deleteAlarm(int position) {
        if (position >= 0 && position < alarms.size()) {
            alarms.remove(position);
            notifyItemRemoved(position);
        }
    }
    // Method to start the alarm
    private void startAlarm(Context context, Alarm alarm) {
        // Create an intent for the alarm receiver
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("message", "Alarm ringing!"); // You can pass any data you need here

        // Create a PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set up AlarmManager to trigger the alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long alarmTimeMillis = calculateAlarmTimeMillis(alarm); // Calculate the time when the alarm should go off
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeMillis, pendingIntent);
        }
    }

    // Method to stop the alarm
    private void stopAlarm(Context context, Alarm alarm) {
        // Create an intent for the alarm receiver (same as the one used to start the alarm)
        Intent intent = new Intent(context, AlarmReceiver.class);

        // Create a PendingIntent (same as the one used to start the alarm)
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Cancel the PendingIntent to stop the alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    // Method to calculate the time when the alarm should go off
    private long calculateAlarmTimeMillis(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);

        // Check if the alarm time is in the past, if so, set it for the next day
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return calendar.getTimeInMillis();
    }

}
