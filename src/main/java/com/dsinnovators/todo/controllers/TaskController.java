package com.dsinnovators.todo.controllers;

import com.dsinnovators.todo.entities.Task;
import com.dsinnovators.todo.enums.Status;
import com.dsinnovators.todo.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", taskService.getTasks());

        return "pages/index";
    }

    @GetMapping("/task/{id}/view")
    public String show(Model model, @PathVariable("id") Long id) {
        var task = taskService.getTask(id);

        if (task.isPresent()) {
            model.addAttribute("task", task.get());
        }

        return "pages/view";
    }

    @GetMapping("/task/create")
    public String create(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("status", Status.values());

        return "pages/create";
    }

    @PostMapping("/task/create")
    public String create(Model model, @Valid @ModelAttribute("task") Task task, BindingResult errors) {
        if (errors.hasErrors()) {
            model.addAttribute("status", Status.values());

            return "pages/create";
        }

        taskService.createTask(task);

        return "redirect:/";
    }

    @GetMapping("/task/{id}/update")
    public String update(Model model, @PathVariable("id") Long id) {
        model.addAttribute("task", taskService.getTask(id).get());
        model.addAttribute("status", Status.values());

        return "pages/update";
    }

    @PostMapping("/task/{id}/update")
    public String update(Model model, @Valid @ModelAttribute("task") Task task, BindingResult errors) {
        if (errors.hasErrors()) {
            model.addAttribute("status", Status.values());

            return "pages/update";
        }

        taskService.updateTask(task);

        return "redirect:/";
    }

}
