package com.example.todo.controller.task;

import com.example.todo.service.task.TaskEntity;

public record TaskForm(
           long id,
           String summary,
           String description,
           String status
) {
    public static TaskForm toForm(TaskEntity entity) {
        return new TaskForm(
                        entity.id(),
                        entity.summary(),
                        entity.description(),
                        entity.status().name()
        );
    }
}
