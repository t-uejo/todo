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
    private final String CREATE_MODE = "CREATE";
    private final String EDIT_MODE = "EDIT";

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
    public String showCreationForm(@ModelAttribute TaskForm form, Model model){
        model.addAttribute("mode", CREATE_MODE);
        return "tasks/form";
    }

    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return showCreationForm(form, model);
        }
        taskService.create(form.toEntity());
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model){
        model.addAttribute("mode", EDIT_MODE);
        var taskEntity = taskService.findById(id);
        model.addAttribute("taskForm" , TaskForm.fromEntity(taskEntity));
        return "tasks/form";
    }

    @PutMapping("{id}")
    public String update(
            @PathVariable("id") long id,
            @Validated @ModelAttribute TaskForm form,
            BindingResult bindingResult,
            Model model
    ){
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", EDIT_MODE);
            return "tasks/form";
        }

        taskService.update(form.toEntity(id));
        return "redirect:/tasks/{id}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id){
        taskService.delete(id);
        return "redirect:/tasks";
    }
}





