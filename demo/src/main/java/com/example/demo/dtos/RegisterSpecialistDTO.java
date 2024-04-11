package com.example.demo.dtos;


import com.example.demo.entity.Location;

import java.util.List;

public class RegisterSpecialistDTO {
    private String firstName = "";

    private String lastName = "";

    private String email;

    private String phoneNumber;

    private String address;


    private String password;
    private String specialty;

    private String description;

    private Double price;

    private String appointmentTime;

    private List<NewLocationDTO> locations;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getString() {
        return specialty;
    }

    public void setString(String specialty) {
        this.specialty = specialty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public List<NewLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<NewLocationDTO> locations) {
        this.locations = locations;
    }

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


}
