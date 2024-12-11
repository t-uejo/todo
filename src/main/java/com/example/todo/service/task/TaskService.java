package com.example.todo.service.task;

import com.example.todo.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskEntity> findAll(){
        return taskRepository.select();
    }

    public TaskEntity findById(long taskId) {
        return taskRepository.selectById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: id = " + taskId));
    }
}
