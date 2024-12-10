package com.example.todo.controller.task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TaskController {

    @GetMapping("/tasks")
    public String list(Model model){
        var taskForm1 = new TaskForm(1L, "Spring Bootを学ぶ", "TODOアプリケーションを作ってみる", "DONE");
        var taskForm2 = new TaskForm(2L, "Spring Bootを学ぶ", "TODOアプリケーションを作ってみる", "DONE");

        var taskList = List.of(taskForm1,taskForm2);
        model.addAttribute("taskList", taskList);
        return "tasks/list";
    }
}
