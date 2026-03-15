package com.project.task_api.repository;

import com.project.task_api.dto.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(String id);

    List<Task> findAll();

    void delete(String id);

}
