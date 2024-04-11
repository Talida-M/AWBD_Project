package com.example.demo.services;

import com.example.demo.dtos.ReviewDTO;
import com.example.demo.entity.DoctorReview;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.Specialist;
import com.example.demo.repositories.DoctorReviewsRepo;
import com.example.demo.repositories.PacientsRepo;
import com.example.demo.repositories.SpecialistRepo;
import com.example.demo.services.ReviewsService.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private SpecialistRepo specialistRepo;

    @Mock
    private PacientsRepo pacientsRepo;

    @Mock
    private DoctorReviewsRepo reviewsRepo;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void whenPacientAndSpecialistExist_createReview_savesNewReview() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO(null, "Great service", LocalDateTime.now(), 5, "user123", "specialist@example.com");
        Pacient existingPacient = new Pacient(); // assuming Pacient is a valid entity
        existingPacient.setUsername(reviewDTO.getUsername());
        Specialist existingSpecialist = new Specialist(); // assuming Specialist is a valid entity


        when(pacientsRepo.getPacientByUsername(reviewDTO.getUsername())).thenReturn(Optional.of(existingPacient));
        when(specialistRepo.getDoctorByEmail(reviewDTO.getEmailSpecialist())).thenReturn(Optional.of(existingSpecialist));

        // Act
        reviewService.createReview(reviewDTO);

        // Assert
        verify(reviewsRepo).save(any(DoctorReview.class));
    }
    @Test
    void getDoctorReview_returnsListOfReviewDTO() {
        // Arrange
        Integer specialistId = 1;
        List<ReviewDTO> expectedList = Arrays.asList(new ReviewDTO());
        when(reviewsRepo.getDoctorReview(specialistId)).thenReturn(expectedList);

        // Act
        List<ReviewDTO> result = reviewService.getDoctorReview(specialistId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedList.size(), result.size());
    }
    @Test
    void getDoctorReviews_returnsListOfDoctorReview() {
        // Arrange
        List<DoctorReview> expectedList = Arrays.asList(new DoctorReview());
        when(reviewsRepo.getDoctorReviews()).thenReturn(expectedList);

        // Act
        List<DoctorReview> result = reviewService.getDoctorReviews();

        // Assert
        assertNotNull(result);
        assertEquals(expectedList.size(), result.size());
    }
    @Test
    void whenReviewExists_delete_deletesTheReview() {
        // Arrange
        Integer reviewId = 1;
        DoctorReview existingReview = new DoctorReview();
        existingReview.setId(reviewId);
        when(reviewsRepo.findById(reviewId)).thenReturn(Optional.of(existingReview));

        // Act
        reviewService.delete(reviewId);

        // Assert
        verify(reviewsRepo).delete(existingReview);
    }
    @Test
    void whenReviewDoesNotExist_delete_throwsNoSuchElementException() {
        // Arrange
        Integer reviewId = 1;
        when(reviewsRepo.findById(reviewId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> reviewService.delete(reviewId));
        verify(reviewsRepo, times(0)).delete(any(DoctorReview.class));
    }

}
