package com.example.demo.controller;


import com.example.demo.dtos.AppointmentDTO;
import com.example.demo.dtos.NewAppointmentDTO;
import com.example.demo.dtos.PaymentDTO;
import com.example.demo.entity.PaymentDetail;
import com.example.demo.repositories.PaymentRepo;
import com.example.demo.services.AppointmentsService.IAppointmentsService;
import com.example.demo.services.PaymentService.IPaymentService;
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
@RequestMapping("/payment")
@Tag(name = "Payment Details Controller", description = "Endpoints for managing payments")

public class PaymentController {
    @Autowired
    public IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Payment for appointment", responses = {
            @ApiResponse(responseCode = "200", description = "payment has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/newpay")
    public ResponseEntity<Void> create(
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details", required = true) PaymentDTO newA){
        paymentService.createPayment(newA);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Payments", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getPay}")
    public ResponseEntity<List<PaymentDetail>> getPayments()
    {
        return ResponseEntity.ok(paymentService.getPayments());
    }
}
