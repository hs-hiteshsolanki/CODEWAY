package com.codeway_task.to_do_list.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.codeway_task.to_do_list.R;
import com.codeway_task.to_do_list.database.DatabaseClient;
import com.codeway_task.to_do_list.database.DatabaseUtils;
import com.codeway_task.to_do_list.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskCompletedAdapter extends RecyclerView.Adapter<TaskCompletedAdapter.TaskCompletedViewHolder> {

    private Context context;
    private List<Task> taskList;
    private OnTaskClickListener onTaskClickListener;


    public TaskCompletedAdapter(Context context, List<Task> taskList, OnTaskClickListener onTaskClickListener) {
        this.context = context;
        this.taskList = taskList;
        this.onTaskClickListener = onTaskClickListener;
        updateTasks(taskList);
        Log.d("TaskCompletedAdapter", "Task list size: " + taskList.size());
    }

    @NonNull
    @Override
    public TaskCompletedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_task_completed, parent, false);
        return new TaskCompletedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskCompletedViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTaskTitle());
        holder.description.setText(task.getTaskDescrption());
        holder.time.setText(task.getLastAlarm());
        holder.status.setText(task.isComplete() ? "COMPLETED" : "UPCOMING");

        try {
            Date date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = DatabaseUtils.getDateFromString(task.getDate());
            }
            String outputDateString = new SimpleDateFormat("EE dd MMM yyyy", Locale.US).format(date);

//            Date date = DateUtils.getDateFromString(task.getDate());
//            String outputDateString = new SimpleDateFormat("EE dd MMM yyyy", Locale.US).format(date);

            String[] items1 = outputDateString.split(" ");
            String day = items1[0];
            String dd = items1[1];
            String month = items1[2];

            holder.day.setText(day);
            holder.date.setText(dd);
            holder.month.setText(month);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public interface OnTaskClickListener {
        void onTaskDeleted(Task task);
    }
    public void updateTasks(List<Task> newTasks) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return taskList.size();
            }

            @Override
            public int getNewListSize() {
                return newTasks.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                Task oldTask = taskList.get(oldItemPosition);
                Task newTask = newTasks.get(newItemPosition);
                return oldTask.getTaskId() == newTask.getTaskId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Task oldTask = taskList.get(oldItemPosition);
                Task newTask = newTasks.get(newItemPosition);
                return oldTask.equals(newTask);
            }
        });
        taskList = newTasks;
        diffResult.dispatchUpdatesTo(this);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TaskDiffCallback(taskList, newTasks));
//        taskList = newTasks;
//        diffResult.dispatchUpdatesTo(this);
    }
    public class TaskCompletedViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        @BindView(R.id.day)
        TextView day;
        @NonNull
        @BindView(R.id.date)
        TextView date;
        @NonNull
        @BindView(R.id.month)
        TextView month;
        @NonNull
        @BindView(R.id.title)
        TextView title;
        @NonNull
        @BindView(R.id.description)
        TextView description;
        @NonNull
        @BindView(R.id.status)
        TextView status;
        @NonNull
        @BindView(R.id.time)
        TextView time;

        TaskCompletedViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}