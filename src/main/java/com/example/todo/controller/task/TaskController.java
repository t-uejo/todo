package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String list(Model model){
        var taskList = taskService.findAll()
                .stream()
                .map(TaskDTO::fromEntity)
                .toList();

        model.addAttribute("taskList", taskList);
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long taskId, Model model) {
        var taskEntity = taskService.findById(taskId);
        model.addAttribute("task" , TaskDTO.fromEntity(taskEntity));
        return "tasks/detail";
    }

    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute TaskForm form){
        return "tasks/form";
    }

    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return showCreationForm(form);
        }
        taskService.create(form.toEntity());
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/editForm")
    public String showEditForm(@PathVariable("id") long taskId, Model model){
        var taskEntity = taskService.findById(taskId);
        model.addAttribute("taskForm" , TaskForm.fromEntity(taskEntity));
        return "tasks/form";
    }
}





