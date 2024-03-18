package com.codeway_task.to_do_list.database;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.codeway_task.to_do_list.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseUtils {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Date getDateFromString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.US);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<Task> getCompletedTasks(OnDataBaseAction databaseAction) {
        return databaseAction.getCompletedTasks();
    }
}