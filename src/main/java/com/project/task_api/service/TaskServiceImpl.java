package com.project.task_api.service;

import com.project.task_api.dto.CreateTask;
import com.project.task_api.dto.Task;
import com.project.task_api.dto.TaskStatus;
import com.project.task_api.dto.UpdateTask;
import com.project.task_api.exception.TaskNotFoundException;
import com.project.task_api.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public Task createTask(CreateTask request) {
        Task task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setStatus(TaskStatus.PENDING);

        return taskRepository.save(task);
    }

    @Override
    public Task getTask(String id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));    }

    @Override
    public Task updateTask(String id, UpdateTask request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if (request.getTitle() != null)
            task.setTitle(request.getTitle());

        if (request.getDescription() != null)
            task.setDescription(request.getDescription());

        if (request.getStatus() != null)
            task.setStatus(request.getStatus());

        if (request.getDueDate() != null)
            task.setDueDate(request.getDueDate());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task.getId());
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
