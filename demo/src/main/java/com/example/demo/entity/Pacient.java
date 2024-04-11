package com.example.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pacient")
public class Pacient  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pacientId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "socialCategory")
    private String socialCategory;
    @Column(name = "username")
    private String username;



    @OneToMany(mappedBy = "pacient")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "pacient")
    private List<DoctorReview> doctorReviews;
    @Column(name = "hasInsurance")
    private boolean hasInsurance;

    @JoinColumn(name = "user_page_journal_fk")
    @OneToOne()
    private UserPageJournal userPageJournal;
    public  Pacient(){}

    public Pacient(String socialCategory, boolean hasInsurance, String username) {
        this.socialCategory = socialCategory;
        this.hasInsurance = hasInsurance;
        this.username = username;
    }

    public Pacient(Integer pacientId, User user, String socialCategory, boolean hasInsurance, String username) {
        this.pacientId = pacientId;
        this.user = user;
        this.socialCategory = socialCategory;
        this.hasInsurance = hasInsurance;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPacientId() {
        return pacientId;
    }

    public void setPacientId(Integer pacientId) {
        this.pacientId = pacientId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Pacient{" +
                "pacientId=" + pacientId +
                ", user=" + user +
                ", socialCategory=" + socialCategory +
                ", username='" + username + '\'' +
                ", appointments=" + appointments +
                ", doctorReviews=" + doctorReviews +
                ", hasInsurance=" + hasInsurance +
                ", userPageJournal=" + userPageJournal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pacient)) return false;
        Pacient pacient = (Pacient) o;
        return isHasInsurance() == pacient.isHasInsurance() && Objects.equals(getPacientId(), pacient.getPacientId()) && Objects.equals(getUser(), pacient.getUser()) && getSocialCategory() == pacient.getSocialCategory() && Objects.equals(getUsername(), pacient.getUsername()) && Objects.equals(appointments, pacient.appointments) && Objects.equals(doctorReviews, pacient.doctorReviews) && Objects.equals(userPageJournal, pacient.userPageJournal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPacientId(), getUser(), getSocialCategory(), getUsername(), appointments, doctorReviews, isHasInsurance(), userPageJournal);
    }
}