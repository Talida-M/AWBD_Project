package com.example.demo.controller;

import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.services.PacientService.IPacientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PacientController.class)
@ActiveProfiles("h2")
@AutoConfigureTestDatabase
@SpringJUnitWebConfig
public class PacientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private IPacientService pacientService;
    private static final Logger log = LoggerFactory.getLogger(PacientControllerTest.class);

    @Test
    @WithMockUser(username="user")
    public void createPacient() throws Exception {
        RegisterPacientDTO newPacient = new RegisterPacientDTO("John", "Doe", "johndoe@example.com", "1234567890", "123 Street", "password", "Social Category", "username", true);

        mockMvc.perform((RequestBuilder) post("/pacient/signUpP")
                        .contentType("application/json")
                        .with(csrf())
                .content(objectMapper.writeValueAsString(newPacient)))
                .andExpect(status().isOk());
        verify(pacientService).registerPacient(any(RegisterPacientDTO.class));
        log.info("createPacient test executed successfully");

    }



    @Test
    @WithMockUser(username="user", roles={ "SPECIALIST"})
    public void getPacients() throws Exception {
        List<PacientDTO> pacientList = Arrays.asList(new PacientDTO(1,"John", "Doe", "johndoe@example.com", "1234567890", "123 Street",  "Social Category", "username", true)); // Populate with test data
        when(pacientService.getPacients()).thenReturn(pacientList);
        mockMvc.perform(get("/pacient/getPacients")
                .contentType("application/json")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(pacientList.size())));

        verify(pacientService).getPacients();
        log.info("getPacients test executed successfully");

    }

    @Test
    @WithMockUser(username="user", roles={ "ADMIN", "PACIENT"})
    public void deletePacient() throws Exception {
        Integer pacientId = 1;

        mockMvc.perform(patch("/pacient/delete/{id}", pacientId)
                        .contentType("application/json")
                        .with(csrf())
                        .content(String.valueOf(Long.parseLong(objectMapper.writeValueAsString(pacientId)))))
                .andExpect(status().isNoContent());

        verify(pacientService).delete(pacientId);
        log.info("deletePacient test executed successfully");

    }


}