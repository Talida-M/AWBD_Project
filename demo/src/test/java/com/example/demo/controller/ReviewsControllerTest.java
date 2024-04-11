package com.example.demo.controller;


import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.dtos.ReviewDTO;
import com.example.demo.entity.DoctorReview;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.Specialist;
import com.example.demo.entity.User;
import com.example.demo.services.ReviewsService.IReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewsController.class)
public class ReviewsControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;
        @MockBean
        private IReviewService reviewService;



        @Test
        public void createReview() throws Exception {
            ReviewDTO reviewDTO = new ReviewDTO(1,"ddff", LocalDateTime.now(), 4, "username", "tt@yahoo.com" );



            mockMvc.perform((RequestBuilder) post("/reviews/newReview")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(reviewDTO)))
                    .andExpect(status().isOk());


        }

        @Test
        public void getDoctorReviews() throws Exception {
            int specialistId = 1; // Specify a valid specialistId
            List<ReviewDTO> reviewDTO = Arrays.asList( new ReviewDTO(1,"ddff", LocalDateTime.now(), 4, "username", "tt@yahoo.com" ));

            when(reviewService.getDoctorReview(specialistId)).thenReturn(reviewDTO);

            mockMvc.perform(get("/reviews/getDoctorReviews/1", specialistId)
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(reviewDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(reviewDTO.size())));
            ;
            verify(reviewService).getDoctorReview(specialistId);
        }

        @Test
        public void deleteReview() throws Exception {
            int id = 1;

            mockMvc.perform(patch("/reviews/delete/{id}", id)
                            .contentType("application/json")
                            .content(String.valueOf(Long.parseLong(objectMapper.writeValueAsString(id)))))
                    .andExpect(status().isNoContent());
            verify(reviewService).delete(id);
        }

        @Test
        public void getAllReviews() throws Exception {
            User u = new User(1, "dd", "ddd", "a@yahoo.com", "098777666","dddd", "pp", "Specialist");
            Pacient pa = new Pacient(2, u, "E", true, "uu");
            User u2 = new User(1, "dd", "ddd", "a@yahoo.com", "098777666","dddd", "pp", "Specialist");

            Specialist sp = new Specialist(2, "Spp", "dddd", 222.2, "23", u2);
            List<DoctorReview> reviewDTO = Arrays.asList( new DoctorReview(1, pa, sp, "ddff", LocalDateTime.now(), 4 ));

            when(reviewService.getDoctorReviews()).thenReturn(reviewDTO);


            when(reviewService.getDoctorReviews()).thenReturn(reviewDTO);

            mockMvc.perform(get("/reviews/getAllReviews")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(reviewDTO)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(reviewDTO.size())));

            verify(reviewService).getDoctorReviews();
        }
    }

