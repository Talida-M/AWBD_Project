package com.example.demo.dtos;


import com.example.demo.entity.AppType;
import com.example.demo.entity.StatusAppointment;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Integer AppointmentId;
    private String appointmentType;
    private LocalDateTime appointmentDate;
    private String appointmentStatus;
    private String specialistName;
    private String specialistEmail;
    private String pacientName;
    private String pacientEmail;

    public AppointmentDTO() {
    }

    public AppointmentDTO(Integer appointmentId, String appointmentType, LocalDateTime appointmentDate, String appointmentStatus, String pacientName, String pacientEmail) {
        AppointmentId = appointmentId;
        this.appointmentType = appointmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentStatus = appointmentStatus;
        this.pacientName = pacientName;
        this.pacientEmail = pacientEmail;
    }



    public Integer getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        AppointmentId = appointmentId;
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

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public String getSpecialistEmail() {
        return specialistEmail;
    }

    public void setSpecialistEmail(String specialistEmail) {
        this.specialistEmail = specialistEmail;
    }

    public String getPacientName() {
        return pacientName;
    }

    public void setPacientName(String pacientName) {
        this.pacientName = pacientName;
    }

    public String getPacientEmail() {
        return pacientEmail;
    }

    public void setPacientEmail(String pacientEmail) {
        this.pacientEmail = pacientEmail;
    }
}
