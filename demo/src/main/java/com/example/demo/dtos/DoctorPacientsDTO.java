package com.example.demo.dtos;


import com.example.demo.entity.SocialCategory;

public class DoctorPacientsDTO {
    private String firstName = "";

    private String lastName = "";

    private String email;

    private String phoneNumber;
    private String socialCategory;
    private boolean hasInsurance;
    private Long numberofAppointments;

    public DoctorPacientsDTO() {
    }

    public DoctorPacientsDTO(String firstName, String lastName, String email, String phoneNumber, String socialCategory, boolean hasInsurance, Long numberofAppointments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.socialCategory = socialCategory;
        this.hasInsurance = hasInsurance;
        this.numberofAppointments = numberofAppointments;
    }

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

    public String getSocialCategory() {
        return socialCategory;
    }

    public void setSocialCategory(String socialCategory) {
        this.socialCategory = socialCategory;
    }

    public boolean isHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public Long getNumberofAppointments() {
        return numberofAppointments;
    }

    public void setNumberofAppointments(Long numberofAppointments) {
        this.numberofAppointments = numberofAppointments;
    }
}
