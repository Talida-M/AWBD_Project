package com.example.demo.controller;

import com.example.demo.dtos.AppointmentDTO;
import com.example.demo.dtos.JournalPagesDTO;
import com.example.demo.dtos.NewAppointmentDTO;
import com.example.demo.dtos.NewPageDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.services.AppointmentsService.IAppointmentsService;
import com.example.demo.services.JournalService.IJournalPageService;
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
@RequestMapping("/journalPages")
@Tag(name = "Journal Controller", description = "Endpoints for managing pages of pacient's journal")

public class JournalPageController {

    @Autowired
    public IJournalPageService journalPageService;

    public JournalPageController(IJournalPageService journalPageService) {
        this.journalPageService = journalPageService;
    }


    @Operation(summary = "Create a new journal page", responses = {
            @ApiResponse(responseCode = "200", description = "Page has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/newpage")
    public ResponseEntity<Void> create(
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Page  details", required = true) NewPageDTO newA){
        journalPageService.createPage(newA);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Pacient  Journal Pages", responses = {
            @ApiResponse(responseCode = "200", description = "Pages retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getpages/{pacientId}")
    public ResponseEntity<List<JournalPagesDTO>> getUserJournalPages(
            @PathVariable @Parameter(description = "pacient ID", required = true) Integer pacientId){
        return ResponseEntity.ok(journalPageService.getUserJournalPages(pacientId));
    }


    @Operation(summary = "Update  page  visibility.", responses = {
            @ApiResponse(responseCode = "200", description = "status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/changestatus/{status}/{pageId}")
    public ResponseEntity<Void> changestatus(
            @PathVariable @Parameter(description = "status", required = true) boolean status,
            @PathVariable @Parameter(description = "Page ID", required = true) Integer pageId){
        journalPageService.changestatus(status, pageId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Pacient's Public Pages For Specialist", responses = {
            @ApiResponse(responseCode = "200", description = "Pages retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getpagesForSpecialist/{pacientId}")
    public ResponseEntity<List<JournalPagesDTO>> getPacientJournalPagesBySpecialist(
            @PathVariable @Parameter(description = "pacient ID", required = true) Integer pacientId){
        return ResponseEntity.ok(journalPageService.getPacientJournalPagesBySpecialist(pacientId));
    }

}
