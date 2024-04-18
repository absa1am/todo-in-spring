package com.dsinnovators.todo.controllers;

import com.dsinnovators.todo.entities.Task;
import com.dsinnovators.todo.enums.Status;
import com.dsinnovators.todo.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size) {
        page = (page < 0)? 0 : page;
        size = (size < 1)? 1 : size;

        model.addAttribute("currentPage", page);
        model.addAttribute("currentSize", size);
        model.addAttribute("tasks", taskService.getTasks(page, size));

        return "pages/index";
    }

    @GetMapping("/task/{id}/view")
    public String show(Model model, @PathVariable("id") Long id) {
        var task = taskService.getTask(id);

        if (task.isPresent()) {
            model.addAttribute("task", task.get());
        } else {
            return "error/404";
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
    public String create(Model model, @Valid @ModelAttribute("task") Task task, BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("status", Status.values());

            return "pages/create";
        }

        taskService.createTask(task);

        redirectAttributes.addFlashAttribute("message", "New task created successfully.");

        return "redirect:/";
    }

    @GetMapping("/task/{id}/update")
    public String update(Model model, @PathVariable("id") Long id) {
        Optional<Task> task = taskService.getTask(id);

        if (task.isEmpty()) {
            return "error/404";
        }

        model.addAttribute("task", task.get());
        model.addAttribute("status", Status.values());

        return "pages/update";
    }

    @PostMapping("/task/{id}/update")
    public String update(Model model, @Valid @ModelAttribute("task") Task task, BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("status", Status.values());

            return "pages/update";
        }

        taskService.updateTask(task);

        redirectAttributes.addFlashAttribute("message", "Task updated successfully.");

        return "redirect:/";
    }

    @PostMapping("/task/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        taskService.deleteTask(id);

        redirectAttributes.addFlashAttribute("message", "Task deleted successfully.");

        return "redirect:/";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download() throws IOException {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tasks.csv")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(taskService.loadCsv());
    }

}
