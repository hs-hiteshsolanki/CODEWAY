package com.codeway_task.alarm_clock_app;

public class Alarm {
    private int id;
    int hour;
    int minute;
    boolean isEnabled;

    public Alarm(int hour, int minute) {

        this.hour = hour;
        this.minute = minute;
        this.isEnabled = true; // By default, new alarm is enabled
    }
    public Alarm(int id, int hour, int minute) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.isEnabled = true; // By default, new alarm is enabled
    }
    // Add a getter method for the ID field
    public int getId() {
        return id;
    }
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
    // Helper method to get a string representation of the alarm time
    public String getTimeString() {
        return String.format("%02d:%02d", hour, minute);
    }
}
