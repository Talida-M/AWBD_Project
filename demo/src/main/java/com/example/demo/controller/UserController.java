package com.example.demo.controller;



import com.example.demo.dtos.LoginDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserUpdateDTO;
import com.example.demo.entity.User;
import com.example.demo.services.UserService.IUserService;
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
@RequestMapping("/user")
@Tag(name = "User Controller")
public class UserController {
    @Autowired
    public IUserService userService;


    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Update  user.", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<User> update(
            @PathVariable @Parameter(description = "User ID", required = true) Integer id,
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated user details", required = true) UserUpdateDTO updateUser){
        return ResponseEntity.ok(userService.update(id, updateUser));
    }

    @Operation(summary = "Login and retrieve user details (user ID, email, TOKEN)", responses = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/login/{email}")
    public LoginDTO login(
            @PathVariable @Parameter(description = "User email", required = true) String email,
            @RequestHeader(name = "password") @Parameter(description = "User password", required = true) String password){
        return ResponseEntity.ok(userService.login(email, password)).getBody();
    }

    @Operation(summary = "Get all the users ", responses = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }




}
