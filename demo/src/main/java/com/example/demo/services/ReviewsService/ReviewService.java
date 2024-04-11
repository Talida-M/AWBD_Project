package com.example.demo.services.ReviewsService;

import com.example.demo.dtos.ReviewDTO;
import com.example.demo.entity.DoctorReview;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.Specialist;
import com.example.demo.repositories.DoctorReviewsRepo;
import com.example.demo.repositories.PacientsRepo;
import com.example.demo.repositories.SpecialistRepo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService implements  IReviewService{
    @Autowired
      SpecialistRepo specialistRepo;
    @Autowired
      PacientsRepo pacientsRepo;
    @Autowired
      DoctorReviewsRepo reviewsRepo;

    public ReviewService(SpecialistRepo specialistRepo, PacientsRepo pacientsRepo, DoctorReviewsRepo reviewsRepo) {
        this.specialistRepo = specialistRepo;
        this.pacientsRepo = pacientsRepo;
        this.reviewsRepo = reviewsRepo;
    }

    @Transactional
    @Override
    public List<ReviewDTO> getDoctorReview(Integer specialistId) {
        return reviewsRepo.getDoctorReview(specialistId);
    }


    @Transactional
    @Override
    public void createReview(ReviewDTO reviewDTO){
        Pacient pacient = pacientsRepo.getPacientByUsername(reviewDTO.getUsername()).orElse(null);
        Specialist specialist = specialistRepo.getDoctorByEmail(reviewDTO.getEmailSpecialist()).orElse(null);
        DoctorReview newapp = new DoctorReview();
        newapp.setCommentDate(reviewDTO.getCommentDate());
        newapp.setReview(reviewDTO.getReview());
        newapp.setStarsNumber(reviewDTO.getStarsNumber());
        newapp.setPacient(pacient);
        newapp.setSpecialist(specialist);
        reviewsRepo.save(newapp);

    }

    @Override
    public List<DoctorReview> getDoctorReviews() {
        return reviewsRepo.getDoctorReviews();
    }

    @Override
    public void delete(Integer id) {
        DoctorReview app = reviewsRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("Review does not exist"));
        reviewsRepo.delete(app);

    }
}
