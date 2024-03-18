package com.codeway_task.to_do_list.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.codeway_task.to_do_list.model.Task;

import java.util.List;

public class TaskDiffCallback extends DiffUtil.Callback {
    private List<Task> oldTasks;
    private List<Task> newTasks;

    public TaskDiffCallback(List<Task> oldTasks, List<Task> newTasks) {
        this.oldTasks = oldTasks;
        this.newTasks = newTasks;
    }

    @Override
    public int getOldListSize() {
        return oldTasks.size();
    }

    @Override
    public int getNewListSize() {
        return newTasks.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTasks.get(oldItemPosition).getTaskId() == newTasks.get(newItemPosition).getTaskId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Task oldTask = oldTasks.get(oldItemPosition);
        Task newTask = newTasks.get(newItemPosition);
        return oldTask.equals(newTask);}
}