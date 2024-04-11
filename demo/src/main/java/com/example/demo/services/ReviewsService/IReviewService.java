package com.example.demo.services.ReviewsService;

import com.example.demo.dtos.ReviewDTO;
import com.example.demo.entity.DoctorReview;

import java.util.List;


public interface IReviewService {
    List<ReviewDTO> getDoctorReview(Integer specialistId);
    void delete(Integer id);
    void createReview(ReviewDTO reviewDTO);
    List<DoctorReview> getDoctorReviews();

}
