package com.example.demo.controller;

import com.example.demo.dtos.JournalPagesDTO;
import com.example.demo.dtos.NewPageDTO;
import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.services.JournalService.IJournalPageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JournalPageController.class)
@ActiveProfiles("h2")
@AutoConfigureTestDatabase
@SpringJUnitWebConfig
public class JournalPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IJournalPageService journalPageService;
    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger log = Logger.getLogger(JournalPageControllerTest.class.getName());

    @Test
    public void createJournalPage() throws Exception {
        log.info("Starting createJournalPage test...");
        NewPageDTO newPacient = new NewPageDTO(LocalDateTime.now(), "johndoe@example.com", "1234567890", true);

        mockMvc.perform(post("/journalPages/newpage")
                        .contentType("application/json")
                       .content(objectMapper.writeValueAsString(newPacient)))
                .andExpect(status().isOk());
        verify(journalPageService).createPage(any(NewPageDTO.class));
        log.info("createJournalPage test completed successfully.");
    }

    @Test
    public void getUserJournalPages() throws Exception {
        int pacientId = 1;

        List<JournalPagesDTO> pacientList = Arrays.asList(new JournalPagesDTO(1, LocalDateTime.now(), "jjj", "kkkkk", true));

        mockMvc.perform(get("/journalPages/getpages/1", pacientId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(journalPageService).getUserJournalPages(pacientId);
    }

    @Test
    public void changePageStatus() throws Exception {
        boolean status = true; // Specify a valid status
        int pageId = 1; // Specify a valid pageId

        log.info("Starting changePageStatus test...");


        mockMvc.perform(patch("/journalPages/changestatus/{status}/{pageId}", status, pageId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(status)))
                .andExpect(status().isOk());
        verify(journalPageService).changestatus(status, pageId);
        log.info("changePageStatus test completed successfully.");

    }

    @Test
    public void getPacientJournalPagesBySpecialist() throws Exception {
        Integer pacientId = 1;
        log.info("Starting getPacientJournalPagesBySpecialist test...");

        List<JournalPagesDTO> pacientList = Arrays.asList(new JournalPagesDTO(1, LocalDateTime.now(), "jjj", "kkkkk", true));

        mockMvc.perform(get("/journalPages/getpagesForSpecialist/1", pacientId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(journalPageService).getPacientJournalPagesBySpecialist(pacientId);
        log.info("getPacientJournalPagesBySpecialist test completed successfully.");

    }
}
