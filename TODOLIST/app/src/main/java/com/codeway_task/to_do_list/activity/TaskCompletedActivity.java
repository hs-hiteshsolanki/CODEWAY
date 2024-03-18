package com.codeway_task.to_do_list.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.codeway_task.to_do_list.R;
import com.codeway_task.to_do_list.adapter.TaskCompletedAdapter;
import com.codeway_task.to_do_list.database.DatabaseUtils;
import com.codeway_task.to_do_list.model.Task;
import com.codeway_task.to_do_list.database.DatabaseClient;

import java.util.List;

public class TaskCompletedActivity extends AppCompatActivity {

    private TaskCompletedAdapter taskCompletedAdapter;
    private RecyclerView completedTasksRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_completed);

        completedTasksRecyclerView = findViewById(R.id.completed_tasks_recycler_view);
        completedTasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        new GetCompletedTasks().execute();

    }
    private class GetCompletedTasks extends AsyncTask<Void, Void, List<Task>> {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().dataBaseAction().getCompletedTasks();
            }
            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                if (tasks.size() > 0) {
                    taskCompletedAdapter = new TaskCompletedAdapter(getApplicationContext(), tasks, new TaskCompletedAdapter.OnTaskClickListener() {
                        @Override
                        public void onTaskDeleted(Task task) {

                        }
                    });
                    completedTasksRecyclerView.setAdapter(taskCompletedAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "No completed tasks found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }