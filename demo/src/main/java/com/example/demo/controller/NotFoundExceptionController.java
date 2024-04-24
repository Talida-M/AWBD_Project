package com.example.demo.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class NotFoundExceptionController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}