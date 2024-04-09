package com.dsinnovators.todo.controllers;

import com.dsinnovators.todo.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProgressController {

    private TaskService taskService;

    public ProgressController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/progress")
    public String index(Model model) {
        model.addAttribute("progress", taskService.getProgress());

        return "pages/progress";
    }

}
