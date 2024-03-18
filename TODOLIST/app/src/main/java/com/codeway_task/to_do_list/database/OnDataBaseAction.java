package com.codeway_task.to_do_list.database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codeway_task.to_do_list.model.Task;

import java.util.List;
@Dao
public interface OnDataBaseAction {
    @Query("SELECT * FROM Task")
    List<Task> getAllTasksList();

    @Query("DELETE FROM Task")
    void truncateTheList();

    @Insert
    void insertDataIntoTaskList(Task task);

    @Query("DELETE FROM Task WHERE taskId = :taskId")
    void deleteTaskFromId(int taskId);

    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    Task selectDataFromAnId(int taskId);

    @Query("UPDATE Task SET taskTitle = :taskTitle, taskDescription = :taskDescription, date = :taskDate, " +
            "lastAlarm = :taskTime, event = :taskEvent WHERE taskId = :taskId")
    void updateAnExistingRow(int taskId, String taskTitle, String taskDescription , String taskDate, String taskTime,
                             String taskEvent);

    @Query("SELECT date('now')")
    String getCurrentDate();

    @Query("SELECT * FROM Task WHERE date = :date")
    List<Task> getTasksForDate(String date);
    @Query("SELECT * FROM Task WHERE isCompleted = 1")
    List<Task> getCompletedTasks();
}