package com.example.todo.service.task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    public List<TaskEntity> find(){
        var taskEntity1 = new TaskEntity(1L, "Spring Bootを学ぶ", "TODOアプリケーションを作ってみる", TaskStatus.TODO);
        var taskEntity2 = new TaskEntity(2L, "Spring Bootを学ぶ", "TODOアプリケーションを作ってみる", TaskStatus.DONE);

        return List.of(taskEntity1,taskEntity2);
    }

}
