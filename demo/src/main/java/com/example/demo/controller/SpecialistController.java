package com.example.demo.controller;

import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Specialist;
import com.example.demo.services.SpecialistService.ISpecialistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/specialist")
@Tag(name = "Specialist Controller", description = "Endpoints for managing specialists")

public class SpecialistController {
    @Autowired
    public ISpecialistService specialistService;

    public SpecialistController(ISpecialistService specialistService) {
        this.specialistService = specialistService;
    }
    @Operation(summary = "Create a new specialist", responses = {
            @ApiResponse(responseCode = "200", description = "Specialist has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/signUpS")
    public ResponseEntity<Specialist> create(
             @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Specialist", required = true) RegisterSpecialistDTO newUser){
        specialistService.registerDoctor(newUser);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Get doctor's list of pacients", responses = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getDoctorsList")
    public ResponseEntity<List<SpecialistDTO>> getDoctorsList(){
        return ResponseEntity.ok( specialistService.getDoctorsList( ));
    }

    @Operation(summary = "Get specialist email", responses = {
            @ApiResponse(responseCode = "200", description = "Specialist retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Specialist not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getSpecialistByEmail/{email}")
    public ResponseEntity<Optional<SpecialistDTO>> getSpecialistByEmail(
            @PathVariable @Parameter(description = "Specialist's email", required = true) String email){
        return ResponseEntity.ok(specialistService.getSpecialistByEmail(email));
    }
    @Operation(summary = "Get specialists by full name; The result is a list because we can have more specialists with the same name", responses = {
            @ApiResponse(responseCode = "200", description = "Specialist retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Specialist not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getSpecialistByName/{firstName}/{lastName}")
    public ResponseEntity<List<SpecialistDTO>> getSpecialistByName(
            @PathVariable @Parameter(description = "Specialist's first name", required = true) String firstName,
            @PathVariable @Parameter(description = "Specialist's last name", required = true) String lastName){
        return ResponseEntity.ok(specialistService.getSpecialistByName(firstName, lastName));
    }
    @Operation(summary = "Delete a user by ID.", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/deleteS/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "User ID", required = true) Integer id){
        specialistService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
