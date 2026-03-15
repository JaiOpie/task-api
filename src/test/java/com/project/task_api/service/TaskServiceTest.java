package com.project.task_api.service;

import com.project.task_api.dto.CreateTask;
import com.project.task_api.dto.TaskStatus;
import com.project.task_api.dto.UpdateTask;
import com.project.task_api.entity.Task;
import com.project.task_api.repository.TaskRepository;
import com.project.task_api.util.StateTransitionUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private StateTransitionUtil stateTransitionUtil;

    @InjectMocks
    private TaskServiceImpl service;

    public TaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createTaskSuccessfully() {

        CreateTask request = new CreateTask();
        request.setTitle("Test Task");
        request.setDescription("Testing");
        request.setDueDate(LocalDate.now().plusDays(1));

        Task savedTask = new Task();
        savedTask.setId("1");
        savedTask.setTitle(request.getTitle());
        savedTask.setDescription(request.getDescription());
        savedTask.setStatus(TaskStatus.PENDING);
        savedTask.setDueDate(request.getDueDate());

        when(repository.save(any(Task.class))).thenReturn(savedTask);

        Task result = service.createTask(request);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        assertEquals(TaskStatus.PENDING, result.getStatus());

        verify(repository, times(1)).save(any(Task.class));
    }


    @Test
    void returnTaskWhenIdExists() {

        Task task = new Task();
        task.setId("1");
        task.setTitle("Test");

        when(repository.findById("1")).thenReturn(Optional.of(task));

        Task result = service.getTask("1");

        assertEquals("1", result.getId());

        verify(repository).findById("1");
    }

    @Test
    void throwExceptionWhenTaskNotFound() {

        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getTask("1"));
    }

    @Test
    void updateTaskStatus() {

        Task existing = new Task();
        existing.setId("1");
        existing.setStatus(TaskStatus.PENDING);

        when(stateTransitionUtil.isValidStatusTransition( any(TaskStatus.class),
                any(TaskStatus.class))).thenReturn(true);
        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(any(Task.class))).thenReturn(existing);

        UpdateTask update = new UpdateTask();
        update.setStatus(TaskStatus.IN_PROGRESS);

        Task result = service.updateTask("1", update);

        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());

        verify(repository).save(existing);
    }


    @Test
    void deleteTask() {

        Task task = new Task();
        task.setId("1");

        when(repository.findById("1")).thenReturn(Optional.of(task));

        service.deleteTask("1");

        verify(repository).delete("1");
    }
}
