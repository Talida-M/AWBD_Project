package com.example.demo.controller;
import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.services.SpecialistService.ISpecialistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SpecialistController.class)
public class SpecialistControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ISpecialistService specialistService;
    @Test
    public void createSpecialist() throws Exception {
        RegisterSpecialistDTO newPacient = new RegisterSpecialistDTO("John", "Doe", "johndoe@example.com", "1234567890", "123 Street", "password", "Psychiatry", "dsdsd", 222.00, "40", null);

        mockMvc.perform((RequestBuilder) post("/specialist/signUpS")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newPacient)))
                .andExpect(status().isOk());
        verify(specialistService).registerDoctor(any(RegisterSpecialistDTO.class));
    }



    @Test
    public void getDoctorsList() throws Exception {
        List<SpecialistDTO> pacientList = Arrays.asList(new SpecialistDTO(1,"John", "Doe", "johndoe@example.com", "1234567890", "123 Street",  "Social Category", "username", 222.0, "50")); // Populate with test data
        when(specialistService.getDoctorsList()).thenReturn(pacientList);
        mockMvc.perform(get("/specialist/getDoctorsList")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(pacientList.size())));

        verify(specialistService).getDoctorsList();
    }

    @Test
    public void getSpecialistByEmail() throws Exception {
       String email = "johndoe@example.com";
        SpecialistDTO pacientList = new SpecialistDTO(1,"John", "Doe", "johndoe@example.com", "1234567890", "123 Street",  "Social", "bvbvb", 222.0, "50");
        when(specialistService.getSpecialistByEmail(email)).thenReturn(Optional.of(pacientList));
        mockMvc.perform(get("/specialist/getSpecialistByEmail/johndoe@example.com")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(specialistService).getSpecialistByEmail(email);
    }


    @Test
    public void getSpecialistByName() throws Exception {
        String f = "John";
        String l = "Doe";
        List<SpecialistDTO> pacientList = Arrays.asList(new SpecialistDTO(1,"John", "Doe", "johndoe@example.com", "1234567890", "123 Street",  "Social", "bvbvb", 222.0, "50"));
        when(specialistService.getSpecialistByName(f, l)).thenReturn(pacientList);
        mockMvc.perform(get("/specialist/getSpecialistByName/John/Doe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(specialistService).getSpecialistByName(f, l);
    }

    @Test
    public void delete() throws Exception {
        Integer id = 1;

        mockMvc.perform(patch("/specialist/deleteS/{id}", id)
                        .contentType("application/json")
                        .content(String.valueOf(Long.parseLong(objectMapper.writeValueAsString(id)))))
                .andExpect(status().isNoContent());

        verify(specialistService).delete(id);
    }

}
