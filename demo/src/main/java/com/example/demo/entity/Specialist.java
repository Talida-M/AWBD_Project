package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "specialist")
public class Specialist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specialistId;
    @Column(name = "specialty")
    @NotEmpty(message = "String is mandatory!")
    private String specialty;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "appointment_time")
    private String appointmentTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "specialist")
    private List<DoctorReview> doctorReviews;
    @ManyToMany(mappedBy = "specialists")
    private List<Location> locations;
//    @ManyToMany
//    @JoinTable(name = "specialist_location",
//            joinColumns = @JoinColumn(name = "specialist_id", referencedColumnName = "specialistId"),
//            inverseJoinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"))
//    private List<Location> locations;

    @OneToMany(mappedBy = "specialist")
    private List<Appointment> appointments;
    public  Specialist(){}

    public Specialist(Integer specialistId, String specialty, String description, Double price, String appointmentTime, User user, List<DoctorReview> doctorReviews,  List<Appointment> appointments) {
        this.specialistId = specialistId;
        this.specialty = specialty;
        this.description = description;
        this.price = price;
        this.appointmentTime = appointmentTime;
        this.user = user;
        this.doctorReviews = doctorReviews;
        this.appointments = appointments;
    }

    public Specialist(Integer specialistId, String specialty, String description, Double price, String appointmentTime, User user) {
        this.specialistId = specialistId;
        this.description = description;
        this.price = price;
        this.appointmentTime = appointmentTime;
        this.user = user;
    }


    // Getters and setters for the new fields


    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Integer getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Integer specialistId) {
        this.specialistId = specialistId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DoctorReview> getDoctorReviews() {
        return doctorReviews;
    }

    public void setDoctorReviews(List<DoctorReview> doctorReviews) {
        this.doctorReviews = doctorReviews;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialist)) return false;
        Specialist that = (Specialist) o;
        return Objects.equals(getSpecialistId(), that.getSpecialistId()) && getString() == that.getString() && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getAppointmentTime(), that.getAppointmentTime()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getDoctorReviews(), that.getDoctorReviews()) && Objects.equals(appointments, that.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSpecialistId(), getString(), getDescription(), getPrice(), getAppointmentTime(), getUser(), getDoctorReviews(), appointments);
    }

    @Override
    public String toString() {
        return "Specialist{" +
                "specialty='" + specialty + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", appointmentTime='" + appointmentTime + '\'' +
                '}';
    }


}
