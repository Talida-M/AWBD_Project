package com.example.demo.controller;


import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.services.PacientService.IPacientService;
import com.example.demo.services.SpecialistService.ISpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("register")
public class RegisterController {

    @Autowired
    public ISpecialistService specialistService;
    @Autowired
    public IPacientService pacientService;


    public RegisterController(ISpecialistService specialistService, IPacientService pacientService) {
        this.specialistService = specialistService;
        this.pacientService = pacientService;
    }

    @GetMapping("")
    public ModelAndView selectRole() {
       return new ModelAndView ("selectRole");
    }

    // Endpoint to show patient registration form
    @GetMapping("/patient")
    public ModelAndView registerPatient(Model model) {
        model.addAttribute("patient", new RegisterPacientDTO());
        return new ModelAndView( "registerPatient");
    }

    // Endpoint to show doctor registration form
    @GetMapping("/specialist")
    public ModelAndView registerDoctor(Model model) {
        model.addAttribute("specialist", new RegisterSpecialistDTO());
        return new ModelAndView( "registerSpecialist");
    }

    // Endpoint to handle patient registration submission
    @PostMapping("/signUpP")
    public ModelAndView createPacient(@Valid @ModelAttribute("patient") RegisterPacientDTO patient,
                                         BindingResult bindingResult,
                                         Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("patient",patient);
            return new ModelAndView("registerPatient");}
        pacientService.registerPacient(patient);
        return new ModelAndView( "redirect:/");
    }

    // Endpoint to handle doctor registration submission
    @PostMapping("/signUpS")
    public ModelAndView createSpecialist(@Valid @ModelAttribute("specialist") RegisterSpecialistDTO specialist,
                                         BindingResult bindingResult,
                                         Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("specialist",specialist);
            return new ModelAndView("registerSpecialist");}
        specialistService.registerDoctor(specialist);
        return new ModelAndView( "redirect:/");
    }
}
