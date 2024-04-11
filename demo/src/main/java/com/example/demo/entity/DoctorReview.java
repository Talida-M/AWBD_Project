package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "doctor_reviews")
public class DoctorReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pacient_id")
    private Pacient pacient;

    @ManyToOne(targetEntity = Specialist.class)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Specialist specialist;

    @Column(name = "review", nullable = false)
    private String review;

    @Column(name = "comment_date", nullable = false)
    private LocalDateTime commentDate;

    @Column(name = "stars_number", nullable = false)
    private int starsNumber;

    public DoctorReview(){}
    public DoctorReview(Integer id, Pacient pacient, Specialist specialist, String review, LocalDateTime commentDate, int starsNumber) {
        this.id = id;
        this.pacient = pacient;
        this.specialist = specialist;
        this.review = review;
        this.commentDate = commentDate;
        this.starsNumber = starsNumber;
    }

    // Constructors, getters, setters, and other methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public int getStarsNumber() {
        return starsNumber;
    }

    public void setStarsNumber(int starsNumber) {
        this.starsNumber = starsNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorReview)) return false;
        DoctorReview that = (DoctorReview) o;
        return getStarsNumber() == that.getStarsNumber() && Objects.equals(getId(), that.getId()) && Objects.equals(getPacient(), that.getPacient()) && Objects.equals(getSpecialist(), that.getSpecialist()) && Objects.equals(getReview(), that.getReview()) && Objects.equals(getCommentDate(), that.getCommentDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPacient(), getSpecialist(), getReview(), getCommentDate(), getStarsNumber());
    }

    @Override
    public String toString() {
        return "DoctorReview{" +
                "id=" + id +
                ", pacient=" + pacient +
                ", specialist=" + specialist +
                ", review='" + review + '\'' +
                ", commentDate=" + commentDate +
                ", starsNumber=" + starsNumber +
                '}';
    }
}