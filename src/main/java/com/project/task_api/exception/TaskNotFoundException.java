package com.project.task_api.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String id) {
        super("Task not found with id: " + id);
    }

}