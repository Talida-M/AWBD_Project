package com.example.demo.controller;

import com.example.demo.dtos.*;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.User;
import com.example.demo.services.AppointmentsService.IAppointmentsService;
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
@RequestMapping("/appointment")
@Tag(name = "Appointment Controller", description = "Endpoints for managing appointments")
public class AppointmentController {
    @Autowired
    public IAppointmentsService appointmentsService;

    public AppointmentController(IAppointmentsService appointmentsService) {
        this.appointmentsService = appointmentsService;
    }

    @Operation(summary = "Create a new appointment", responses = {
            @ApiResponse(responseCode = "200", description = "appointment has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/newApp")
    public ResponseEntity<Void> create(
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details", required = true) NewAppointmentDTO newA){
        appointmentsService.newAppointment(newA);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Appointments For Pacients By Status", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAppPS/{status}/{pacientId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsForPacientByStatus(
            @PathVariable @Parameter(description = "status ", required = true) String status,
            @PathVariable @Parameter(description = "pacient ID", required = true) Integer pacientId){
        return ResponseEntity.ok(appointmentsService.getAppointmentsForPacientByStatus(status, pacientId));
    }


    @Operation(summary = "Get Appointments For Pacients By Status", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAppPD/{status}/{specialistID}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsForDoctorByStatus(
            @PathVariable @Parameter(description = "status ", required = true) String status,
            @PathVariable @Parameter(description = "specialist ID", required = true) Integer specialistID){
        return ResponseEntity.ok(appointmentsService.getAppointmentsForDoctorByStatus(status, specialistID));
    }

    @Operation(summary = "Get Appointments For Pacients By Status", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAppS/{emailPacient}/{doctorID}")
    public ResponseEntity<List<AppointmentDTO>> getPacientAppointmentsForSpecialist(
            @PathVariable @Parameter(description = "email pacient ", required = true) String emailPacient,
            @PathVariable @Parameter(description = "doctor ID", required = true) Integer doctorID){
        return ResponseEntity.ok(appointmentsService.getPacientAppointmentsForSpecialist(emailPacient, doctorID));
    }


    @Operation(summary = "Delete a user by ID.", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "Appointment ID", required = true) Integer id){
        appointmentsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update  status appointment.", responses = {
            @ApiResponse(responseCode = "200", description = "status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/update/{status}/{id}")
    public ResponseEntity<Void> update(
            @PathVariable @Parameter(description = "status", required = true) String status,
            @PathVariable @Parameter(description = "ID", required = true) Integer id){
       appointmentsService.updateAppStatus(status, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Appointments For Pacients By Status", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getApps")
    public ResponseEntity<List<Appointment>> getAppointments(){
        return ResponseEntity.ok(appointmentsService.getAppointments());
    }
}
