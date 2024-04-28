package com.example.demo.dtos;

public class UserDetails {
    private Integer id;
    private String firstName = "";
    private String lastName = "";

    private String email;

    public UserDetails() {
    }

    public UserDetails(Integer id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}