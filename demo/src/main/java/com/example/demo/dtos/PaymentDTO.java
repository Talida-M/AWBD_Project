package com.example.demo.dtos;


import com.example.demo.entity.Appointment;

import java.time.LocalDateTime;

public class PaymentDTO {
    private Integer appointment_id;



    private double amount;

    private String currency;
    private LocalDateTime paymentDate;

    public PaymentDTO(Integer appointment_id, double amount, String currency, LocalDateTime paymentDate) {
        this.appointment_id = appointment_id;
        this.amount = amount;
        this.currency = currency;
        this.paymentDate = paymentDate;
    }

    public PaymentDTO() {
    }

    public Integer getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(Integer appointment_id) {
        this.appointment_id = appointment_id;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
