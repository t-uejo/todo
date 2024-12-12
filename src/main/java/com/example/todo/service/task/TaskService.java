package com.example.todo.service.task;

import com.example.todo.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: id = " + taskId));
    }

    public void create(TaskEntity taskEntity) {
        taskRepository.inert(taskEntity);
    }
}
