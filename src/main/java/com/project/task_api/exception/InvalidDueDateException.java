package com.project.task_api.exception;

public class InvalidDueDateException extends RuntimeException {

    public InvalidDueDateException() {
        super("Due date must be a future date");
    }
}
