package com.example.todo.controller.task;

public record TaskForm(
           long id,
           String summary,
           String description,
           String status
) {
}
