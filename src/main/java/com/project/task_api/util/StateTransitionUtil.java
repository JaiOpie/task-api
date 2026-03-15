package com.project.task_api.util;

import com.project.task_api.dto.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class StateTransitionUtil {

    public boolean isValidStatusTransition(TaskStatus current, TaskStatus next) {

        if (current == TaskStatus.DONE && next != TaskStatus.DONE)
            return false;

        if (current == TaskStatus.PENDING && next == TaskStatus.DONE)
            return false;

        return true;
    }
}
