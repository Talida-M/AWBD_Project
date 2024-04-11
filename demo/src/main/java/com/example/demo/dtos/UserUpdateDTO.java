package com.example.demo.dtos;

public class UserUpdateDTO {
    private String lastName;
    private String address;
    private String phoneNumber;

    public UserUpdateDTO(String lastName, String address, String phoneNumber) {
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }



    public String getAddress() {
        return address;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

}
