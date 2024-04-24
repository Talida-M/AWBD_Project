package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//LINK APLICATIE: http://localhost:8080/home

@Controller
public class MainController {
    @RequestMapping({"","/","/home"})
    public ModelAndView getHome(){

        return new ModelAndView("main");
    }

}