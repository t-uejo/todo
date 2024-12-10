package com.example.todo.controller.task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    @GetMapping("/tasks")
    public String list(Model model){
        var taskForm = new TaskForm(1L, "Spring Bootを学ぶ", "TODOアプリケーションを作ってみる", "DONE");
        model.addAttribute("task", taskForm);
        return "tasks/list";
    }
}
