package com.example.demo.controller;

import com.example.demo.dtos.DoctorPacientsDTO;
import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.entity.Pacient;
import com.example.demo.services.PacientService.IPacientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pacient")
@Tag(name = "Pacient Controller", description = "Endpoints for managing pacients")
public class PacientController {
    @Autowired
   public IPacientService pacientService;

    public PacientController(IPacientService pacientService) {
        this.pacientService = pacientService;
    }

    @Operation(summary = "Create a new pacient", responses = {
            @ApiResponse(responseCode = "200", description = "Pacient has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/signUpP")
    public ResponseEntity<Void> create(
          @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details", required = true) RegisterPacientDTO newUser){
        pacientService.registerPacient(newUser);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Get doctor's list of pacients", responses = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getPacientList/{id}")
    public ResponseEntity<DoctorPacientsDTO> getPacientList(
            @PathVariable @Parameter(description = "User ID", required = true) Integer id){
        return ResponseEntity.ok((DoctorPacientsDTO) pacientService.getPacientList( id));
    }

    @Operation(summary = "Get pacients", responses = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getPacients")
    public ResponseEntity<List<PacientDTO>> getPacients(){
        return ResponseEntity.ok(pacientService.getPacients());
    }

    @Operation(summary = "Delete a user by ID.", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "User ID", required = true) Integer id){
        pacientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
