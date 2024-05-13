package com.example.demo.dtos;


import com.example.demo.entity.Location;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RegisterSpecialistDTO {
    private String firstName = "";

    private String lastName = "";

    @Email(message = "Please provide a valid email address")
    private String email;

    private String phoneNumber;

    private String address;


    private String password;
    private String specialty;

    private String description;

    @Min(value = 0,message = "Price should have minimum value zero")
    @Positive(message = "Price should be greater than zero")
    private Double price;

    private String appointmentTime;

    private List<NewLocationDTO> locations;



    public RegisterSpecialistDTO(String firstName, String lastName, String email, String phoneNumber, String address, String password, String specialty, String description, Double price, String appointmentTime, List<NewLocationDTO> locations) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.specialty = specialty;
        this.description = description;
        this.price = price;
        this.appointmentTime = appointmentTime;
        this.locations = locations;
    }
    public RegisterSpecialistDTO(String firstName, String lastName, String email, String phoneNumber, String address, String password, String specialty, String description, Double price, String appointmentTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.specialty = specialty;
        this.description = description;
        this.price = price;
        this.appointmentTime = appointmentTime;
    }


    public RegisterSpecialistDTO() {
        this.locations = new ArrayList<>();
    }
}
