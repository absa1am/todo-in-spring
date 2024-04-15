package com.dsinnovators.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/404")
    public String notFound() {
        return "error/404";
    }

    @GetMapping("/405")
    public String methodNotAllowed() {
        return "error/405";
    }

    @GetMapping("/500")
    public String internalServerError() {
        return "error/500";
    }

}
