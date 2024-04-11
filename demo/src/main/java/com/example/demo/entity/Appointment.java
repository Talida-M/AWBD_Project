package com.example.demo.entity;



import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer AppointmentId;
    @ManyToOne
    @JoinColumn(name = "pacient_id")
    private Pacient pacient;

    @ManyToOne(targetEntity = Specialist.class)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Specialist specialist;

    @Column(name = "appointmentType", nullable = false)
    private String appointmentType;

    @Column(name = "appointmentDate", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(name = "appointmentStatus", nullable = false)
    private String appointmentStatus;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PaymentDetail paymentDetail;



    public Appointment(Integer appointmentId,  Pacient pacient, Specialist specialist, String appointmentType, LocalDateTime appointmentDate, String appointmentStatus) {
        AppointmentId = appointmentId;

        this.pacient = pacient;
        this.specialist = specialist;
        this.appointmentType = appointmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentStatus = appointmentStatus;
    }

    public Appointment() {
    }

    public Integer getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        AppointmentId = appointmentId;
    }



    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(getAppointmentId(), that.getAppointmentId()) && Objects.equals(getPacient(), that.getPacient()) && Objects.equals(getSpecialist(), that.getSpecialist()) && getAppointmentType() == that.getAppointmentType() && Objects.equals(getAppointmentDate(), that.getAppointmentDate()) && getAppointmentStatus() == that.getAppointmentStatus() && Objects.equals(paymentDetail, that.paymentDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppointmentId(), getPacient(), getSpecialist(), getAppointmentType(), getAppointmentDate(), getAppointmentStatus(), paymentDetail);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "AppointmentId=" + AppointmentId +
                ", pacient=" + pacient +
                ", specialist=" + specialist +
                ", appointmentType='" + appointmentType + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentStatus='" + appointmentStatus + '\'' +
                '}';
    }


}
