package com.project.task_api.service;

import com.project.task_api.dto.CreateTask;
import com.project.task_api.entity.Task;
import com.project.task_api.dto.TaskStatus;
import com.project.task_api.dto.UpdateTask;
import com.project.task_api.exception.InvalidDueDateException;
import com.project.task_api.exception.InvalidTaskStatusException;
import com.project.task_api.exception.TaskNotFoundException;
import com.project.task_api.repository.TaskRepository;
import com.project.task_api.util.StateTransitionUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final StateTransitionUtil stateTransitionUtil;

    public TaskServiceImpl(TaskRepository taskRepository, StateTransitionUtil stateTransitionUtil) {
        this.taskRepository = taskRepository;
        this.stateTransitionUtil = stateTransitionUtil;
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

        if (request.getStatus() != null) {

            TaskStatus currentStatus = task.getStatus();
            TaskStatus newStatus = request.getStatus();

            if (!stateTransitionUtil.isValidStatusTransition(currentStatus, newStatus)) {
                throw new InvalidTaskStatusException(
                        "Invalid status transition from " + currentStatus + " to " + newStatus
                );
            }

            task.setStatus(newStatus);
        }

        if (request.getDueDate() != null) {

            if (request.getDueDate().isBefore(LocalDate.now())) {
                throw new InvalidDueDateException();
            }

            task.setDueDate(request.getDueDate());
        }

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task.getId());
    }

    @Override
    public List<Task> getAllTasks(TaskStatus status, int page, int size) {
        List<Task> tasks = taskRepository.findAll();

        if(status!=null) {
            tasks = tasks.stream().filter(task -> task.getStatus() == status).toList();
        }

            int start = page*size;
            int end = Math.min(start + size, tasks.size());

            if(start>tasks.size()){
                return List.of();
            }
            return tasks.subList(start,end);
        }

}
