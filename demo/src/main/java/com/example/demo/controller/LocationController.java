package com.example.demo.controller;

import com.example.demo.dtos.LocationSpecialistsDTO;
import com.example.demo.dtos.NewLocationDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Location;
import com.example.demo.entity.Specialist;
import com.example.demo.services.LocationsService.ILocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/location")
@Tag(name = "Location Controller", description = "Endpoints for managing locations")
public class LocationController {
    @Autowired
    public ILocationService locationService;

    public  LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Create a new location ", responses = {
            @ApiResponse(responseCode = "200", description = "Location has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/newLocation")
    public ResponseEntity<Void> create(
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Location  details", required = true) Location newA){
        locationService.registerLocation(newA);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get address", responses = {
            @ApiResponse(responseCode = "200", description = "successfully"),
            @ApiResponse(responseCode = "404", description = " not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getSpecialistsByLocation/{locationId}")
    public List<SpecialistDTO> getSpecialistsByLocation(
            @PathVariable @Parameter(description = "ID", required = true) long locationId){
        return  locationService.getSpecialistsByLocation( locationId);
    }

    @Operation(summary = "Get address", responses = {
            @ApiResponse(responseCode = "200", description = "successfully"),
            @ApiResponse(responseCode = "404", description = " not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getLocationBySpecialistSpecialists/{idS}")
    public List<NewLocationDTO> getLocationBySpecialists(@PathVariable Integer idS) {
        return locationService.getLocationBySpecialists(idS);
    }


}
