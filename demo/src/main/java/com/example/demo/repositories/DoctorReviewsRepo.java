package com.example.demo.repositories;


import com.example.demo.dtos.ReviewDTO;
import com.example.demo.entity.DoctorReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorReviewsRepo extends JpaRepository<DoctorReview,Integer> {
    @Query("SELECT  NEW com.example.demo.dtos.ReviewDTO(r.id, r.review, r.commentDate, r.starsNumber, p.username, r.specialist.user.email)" +
            " FROM DoctorReview r " +
            " JOIN Pacient p ON p.pacientId = r.pacient.pacientId" +
            " where r.specialist.specialistId = :specialistId " +
            "order by r.commentDate DESC ")
    List<ReviewDTO> getDoctorReview(Integer specialistId);

    @Query("SELECT r" +
            " FROM DoctorReview r " +
            "order by r.commentDate DESC ")
    List<DoctorReview> getDoctorReviews();

}
