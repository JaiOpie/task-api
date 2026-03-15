package com.project.task_api.service;

import com.project.task_api.dto.CreateTask;
import com.project.task_api.dto.Task;
import com.project.task_api.dto.UpdateTask;

import java.util.List;

public interface TaskService {

    Task createTask(CreateTask request);

    Task getTask(String id);

    Task updateTask(String id, UpdateTask request);

    void deleteTask(String id);

    List<Task> getAllTasks();
}
