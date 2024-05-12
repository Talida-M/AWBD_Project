package com.example.demo.controller;
import com.example.demo.dtos.NewLocationDTO;
import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.services.SpecialistService.ISpecialistService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SpecialistController.class)
@ActiveProfiles("h2")
@AutoConfigureTestDatabase
@SpringJUnitWebConfig
public class SpecialistControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ISpecialistService specialistService;
    private static final Logger log = LoggerFactory.getLogger(SpecialistControllerTest.class);

    @Test
    @WithMockUser(username="user", roles={ "SPECIALIST", "ADMIN"})
    public void createSpecialist() throws Exception {
        log.info("Executing createSpecialist test...");

        List<NewLocationDTO> locations = new ArrayList<>();

        locations.add(new NewLocationDTO(1L, "Location 1", "Address 1"));
        locations.add(new NewLocationDTO(2L, "Location 2", "Address 2"));
        RegisterSpecialistDTO newPacient = new RegisterSpecialistDTO("John", "Doe", "johndoe@example.com", "1234567890", "123 Street", "password", "Psychiatry", "dsdsd", 222.00, "40", locations);

        mockMvc.perform((RequestBuilder) post("/specialist/signUpS")
                        .contentType("application/json")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(newPacient)))
                .andExpect(status().isOk());
        verify(specialistService).registerDoctor(any(RegisterSpecialistDTO.class));
        log.info("createSpecialist test completed.");

    }



    @Test
    @WithMockUser(username="user", roles={ "ADMIN", "PACIENT", "SPECIALIST"})
    public void getDoctorsList() throws Exception {
        log.info("Executing getDoctorsList test...");

        List<SpecialistDTO> pacientList = Arrays.asList(new SpecialistDTO(1,"John", "Doe", "johndoe@example.com", "1234567890", "123 Street",  "Social Category", "username", 222.0, "50")); // Populate with test data
        when(specialistService.getDoctorsList()).thenReturn(pacientList);
        mockMvc.perform(get("/specialist/getDoctorsList")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(pacientList.size())));

        verify(specialistService).getDoctorsList();
        log.info("getDoctorsList test completed.");

    }

    @Test
    @WithMockUser(username="user", roles={ "SPECIALIST", "ADMIN", "PACIENT"})
    public void getSpecialistByEmail() throws Exception {
        log.info("Executing getSpecialistByEmail test...");

        String email = "johndoe@example.com";
        SpecialistDTO pacientList = new SpecialistDTO(1,"John", "Doe", "johndoe@example.com", "1234567890", "123 Street",  "Social", "bvbvb", 222.0, "50");
        when(specialistService.getSpecialistByEmail(email)).thenReturn(Optional.of(pacientList));
        mockMvc.perform(get("/specialist/getSpecialistByEmail/johndoe@example.com")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(specialistService).getSpecialistByEmail(email);
        log.info("getSpecialistByEmail test completed.");

    }


    @Test
    @WithMockUser(username="user", roles={ "ADMIN", "PACIENT", "SPECIALIST"})
    public void getSpecialistByName() throws Exception {
        log.info("Executing getSpecialistByName test...");

        String f = "John";
        String l = "Doe";
        List<SpecialistDTO> pacientList = Arrays.asList(new SpecialistDTO(1,"John", "Doe", "johndoe@example.com", "1234567890", "123 Street",  "Social", "bvbvb", 222.0, "50"));
        when(specialistService.getSpecialistByName(f, l)).thenReturn(pacientList);
        mockMvc.perform(get("/specialist/getSpecialistByName/John/Doe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(specialistService).getSpecialistByName(f, l);
        log.info("getSpecialistByName test completed.");

    }

    @Test
    @WithMockUser(username="user", roles={"SPECIALIST", "ADMIN"})
    public void delete() throws Exception {
        log.info("Executing delete test...");

        Integer id = 1;

        mockMvc.perform(patch("/specialist/deleteS/{id}", id)
                        .contentType("application/json")
                        .with(csrf())
                        .content(String.valueOf(Long.parseLong(objectMapper.writeValueAsString(id)))))
                .andExpect(status().isNoContent());

        verify(specialistService).delete(id);
        log.info("delete test completed.");

    }

}
