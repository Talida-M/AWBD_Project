package com.example.demo.dtos;

import java.time.LocalDateTime;

public class ReviewDTO {
    private  Integer id;
    private String review;
    private LocalDateTime commentDate;
    private int starsNumber;
    private String username;

    private String emailSpecialist;

    public ReviewDTO() {
    }



    public ReviewDTO(Integer id,String review, LocalDateTime commentDate, int starsNumber, String username, String emailSpecialist) {
        this.id = id;
        this.review = review;
        this.commentDate = commentDate;
        this.starsNumber = starsNumber;
        this.username = username;
        this.emailSpecialist = emailSpecialist;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailSpecialist() {
        return emailSpecialist;
    }

    public void setEmailSpecialist(String emailSpecialist) {
        this.emailSpecialist = emailSpecialist;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
