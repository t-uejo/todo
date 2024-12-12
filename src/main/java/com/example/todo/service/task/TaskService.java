package com.example.todo.service.task;

import com.example.todo.controller.task.TaskNotFoundException;
import com.example.todo.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(TaskNotFoundException::new);
    }

    @Transactional
    public void create(TaskEntity taskEntity) {
        taskRepository.inert(taskEntity);
    }
}
