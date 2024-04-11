package com.example.demo.controller;

import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.services.PacientService.IPacientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PacientController.class)
public class PacientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private IPacientService pacientService;

    @Test
    public void createPacient() throws Exception {
        RegisterPacientDTO newPacient = new RegisterPacientDTO("John", "Doe", "johndoe@example.com", "1234567890", "123 Street", "password", "Social Category", "username", true);

        mockMvc.perform((RequestBuilder) post("/pacient/signUpP")
                        .contentType("application/json")
                .content(objectMapper.writeValueAsString(newPacient)))
                .andExpect(status().isOk());
        verify(pacientService).registerPacient(any(RegisterPacientDTO.class));
    }



    @Test
    public void getPacients() throws Exception {
        List<PacientDTO> pacientList = Arrays.asList(new PacientDTO(1,"John", "Doe", "johndoe@example.com", "1234567890", "123 Street",  "Social Category", "username", true)); // Populate with test data
        when(pacientService.getPacients()).thenReturn(pacientList);
        mockMvc.perform(get("/pacient/getPacients")
                .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(pacientList.size())));

        verify(pacientService).getPacients();
    }

    @Test
    public void deletePacient() throws Exception {
        Integer pacientId = 1;

        mockMvc.perform(patch("/pacient/delete/{id}", pacientId)
                        .contentType("application/json")
                        .content(String.valueOf(Long.parseLong(objectMapper.writeValueAsString(pacientId)))))
                .andExpect(status().isNoContent());

        verify(pacientService).delete(pacientId);
    }


}