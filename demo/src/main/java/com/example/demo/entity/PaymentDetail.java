package com.example.demo.entity;

import io.swagger.models.auth.In;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "paymentDetail")
public class PaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paymentId;
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment_id;

    @Column(name = "status", nullable = false)
    private String status;


    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "paymentDate", nullable = false)
    private LocalDateTime paymentDate;


    @JoinColumn(name = "appointment_fk")
    @OneToOne
    private Appointment appointment;

    public PaymentDetail(Integer paymentId, Appointment appointment_id, String status,  double amount, String currency, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.appointment_id = appointment_id;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.paymentDate = paymentDate;
    }

    public PaymentDetail() {
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Appointment getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(Appointment appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentDetail)) return false;
        PaymentDetail that = (PaymentDetail) o;
        return Double.compare(getAmount(), that.getAmount()) == 0 && Objects.equals(getPaymentId(), that.getPaymentId()) && Objects.equals(getAppointment_id(), that.getAppointment_id()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getCurrency(), that.getCurrency()) && Objects.equals(getPaymentDate(), that.getPaymentDate()) && Objects.equals(appointment, that.appointment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentId(), getAppointment_id(), getStatus(),  getAmount(), getCurrency(), getPaymentDate());
    }

    @Override
    public String toString() {
        return "PaymentDetail{" +
                "paymentId=" + paymentId +
                ", appointment_id=" + appointment_id +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
