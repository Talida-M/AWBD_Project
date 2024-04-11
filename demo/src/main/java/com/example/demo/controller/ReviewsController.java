package com.example.demo.controller;

import com.example.demo.dtos.DoctorPacientsDTO;
import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.dtos.ReviewDTO;
import com.example.demo.entity.DoctorReview;
import com.example.demo.services.PacientService.IPacientService;
import com.example.demo.services.ReviewsService.IReviewService;
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
@RequestMapping("/reviews")
@Tag(name = "Doctor Reviews Controller", description = "Endpoints for managing reviews")

public class ReviewsController {
    @Autowired
    public IReviewService reviewService;

    public ReviewsController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Create a new review for a specialist", responses = {
            @ApiResponse(responseCode = "200", description = "Review has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/newReview")
    public ResponseEntity<Void> create(
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Review details", required = true) ReviewDTO newR){
        reviewService.createReview(newR);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Get doctor's list of reviews", responses = {
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Reviews not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getDoctorReviews/{specialistId}")
    public ResponseEntity<List<ReviewDTO>> getDoctorReviews(
            @PathVariable @Parameter(description = "User ID", required = true) Integer specialistId){
        return ResponseEntity.ok(reviewService.getDoctorReview( specialistId));
    }



    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "User ID", required = true) Integer id){
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get the list of all reviews", responses = {
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Reviews not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAllReviews")
    public ResponseEntity<List<DoctorReview>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getDoctorReviews( ));
    }

}
