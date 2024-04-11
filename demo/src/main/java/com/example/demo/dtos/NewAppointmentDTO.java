package com.example.demo.dtos;


import com.example.demo.entity.AppType;

import java.time.LocalDateTime;

public class NewAppointmentDTO {

    private  String pacientEmail;
  private String specialistEmail;
  private String appointmentType;
  private LocalDateTime appointmentDate;

    public NewAppointmentDTO(String pacientEmail, String specialistEmail, String appointmentType, LocalDateTime appointmentDate) {
        this.pacientEmail = pacientEmail;
        this.specialistEmail = specialistEmail;
        this.appointmentType = appointmentType;
        this.appointmentDate = appointmentDate;
    }

    public NewAppointmentDTO() {
    }

    public String getSpecialistEmail() {
        return specialistEmail;
    }

    public void setSpecialistEmail(String specialistEmail) {
        this.specialistEmail = specialistEmail;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPacientEmail() {
        return pacientEmail;
    }

    public void setPacientEmail(String pacientEmail) {
        this.pacientEmail = pacientEmail;
    }
}
