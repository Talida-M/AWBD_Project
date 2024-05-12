package com.example.demo.controller;

import com.example.demo.dtos.AppointmentDTO;
import com.example.demo.dtos.NewAppointmentDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.services.AppointmentsService.IAppointmentsService;
import com.example.demo.services.PacientService.IPacientService;
import com.example.demo.services.SpecialistService.ISpecialistService;
import com.example.demo.services.UserService.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.security.core.Authentication;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AppointmentController.class)
//@ActiveProfiles("h2")
public class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAppointmentsService appointmentsService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserService userService;

    @MockBean
    private IPacientService pacientService;

    @MockBean
    private ISpecialistService specialistService;


    @Test
    @WithMockUser(username="user", roles={ "ADMIN", "PACIENT"})
    public void createAppointment() throws Exception {
        NewAppointmentDTO newAppointmentDTO = new NewAppointmentDTO("patient@example.com", "specialist@example.com", "Checkup", LocalDateTime.now());
        mockMvc.perform((RequestBuilder) post("/appointment/newApp")
                        .contentType("application/json")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(newAppointmentDTO)))
                .andExpect(status().isOk());
        verify(appointmentsService).newAppointment(any(NewAppointmentDTO.class));
    }

    @Test
    @WithMockUser(username="user", roles={"ADMIN", "PACIENT", "SPECIALIST"})
    public void getAppointmentsForPacientByStatus() throws Exception {
        String status = "Programare_Acceptata";
        int pacientId = 1;

        java.util.List<AppointmentDTO> pacientList = java.util.List.of(new AppointmentDTO(1, "O", LocalDateTime.now(), "ff", "ewx", "asx", "jj", "jjj"));
        when(appointmentsService.getAppointmentsForPacientByStatus(status, pacientId)).thenReturn(pacientList);

        mockMvc.perform(get("/appointment/getAppPS/{status}/{pacientId}", status, pacientId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(appointmentsService).getAppointmentsForPacientByStatus(status, pacientId);
    }

    @Test
    @WithMockUser(username="user", roles={ "SPECIALIST", "PACIENT"})
    public void getAppointmentsByStatusAsPacient() throws Exception {
        // Arrange
        String status = "In_Asteptare";
        UserDetails mockUserDetails = Mockito.mock(UserDetails.class);
        //Set<GrantedAuthority> authorities = new HashSet<>();
        //authorities.add(new SimpleGrantedAuthority("ROLE_PACIENT"));
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getPrincipal()).thenReturn(mockUserDetails);
        when(mockUserDetails.getUsername()).thenReturn("user@example.com");
        //when(mockUserDetails.getAuthorities()).then((Answer<?>) authorities);


        int userId = 1;
        int pacientId = 1;
        when(userService.getUserByEmail("user@example.com")).thenReturn(userId);
        when(pacientService.getPacientByUserId(userId)).thenReturn(pacientId);

        Page<AppointmentDTO> page = new PageImpl<>(java.util.List.of(new AppointmentDTO(1, "O", LocalDateTime.now(), "ff", "ewx", "asx", "jj", "jjj")));
        when(appointmentsService.getAppointmentsForPacientByStatusPage(status, pacientId, 0, 5)).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/appointment/appointmentList/{status}", status))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("appointments"))
                .andExpect(view().name("appointmentList"));

        verify(appointmentsService).getAppointmentsForPacientByStatusPage(status, pacientId, 0, 5);
    }

    @Test
    @WithMockUser(username="user", roles={ "SPECIALIST", "PACIENT"})
    public void getAppointmentsByStatusAsSpecialist() throws Exception {
        // Arrange
        String status = "booked";
        int userId = 1;
        int specialistId = 1;
        when(userService.getUserByEmail("specialist@example.com")).thenReturn(userId);
        when(specialistService.getSpecialistByUserId(userId)).thenReturn(specialistId);

        Page<AppointmentDTO> appointmentsPage = new PageImpl<>(java.util.List.of(
                new AppointmentDTO(1, "patient@example.com", LocalDateTime.now(), "Checkup", "Dr. Smith", "booked", "Hospital", "Room 101")
        ));
        when(appointmentsService.getAppointmentsForDoctorByAppointmentStatus(status, specialistId, 0, 5)).thenReturn(appointmentsPage);

        // Act & Assert
        mockMvc.perform(get("/appointment/appointmentList/{status}", status))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("appointments"))
                .andExpect(view().name("appointmentList"))
                .andExpect(model().attribute("appointments", appointmentsPage));

        verify(appointmentsService).getAppointmentsForDoctorByAppointmentStatus(status, specialistId, 0, 5);
    }

    @Test
    @WithMockUser(username="user", roles={ "SPECIALIST", "ADMIN"})
    public void getAppointmentsForDoctorByStatus() throws Exception {
        String status = "In_Asteptare"; // Specify a valid status
        int specialistID = 1; // Specify a valid specialistID

        // Setup pagination
        int page = 0;
        int size = 10;

        // Create a PageImpl to mock the Page<AppointmentDTO>
        Page<AppointmentDTO> appointmentsPage = new PageImpl<>(List.of(
                new AppointmentDTO(1, "O", LocalDateTime.now(), "ff", "jj", "jjj")
        ));

        when(appointmentsService.getAppointmentsForDoctorByAppointmentStatus(status, specialistID, page, size))
                .thenReturn(appointmentsPage);

        mockMvc.perform(get("/appointment/getAppPD/{status}/{specialistID}", status, specialistID)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1))); // Assuming you are returning a paged response

        verify(appointmentsService).getAppointmentsForDoctorByAppointmentStatus(status, specialistID, page, size);
    }

    @Test
    @WithMockUser(username="user", roles={ "SPECIALIST", "ADMIN"})
    public void getPacientAppointmentsForSpecialist() throws Exception {
        String emailPacient = "example@example.com";
        int doctorID = 1;


        java.util.List<AppointmentDTO> pacientList = java.util.List.of(new AppointmentDTO(1, "O", LocalDateTime.now(), "ff", "jj", "jjj"));
        when(appointmentsService.getAppointmentsForPacientByStatus(emailPacient, doctorID)).thenReturn(pacientList);
        mockMvc.perform(get("/appointment/getAppS/{emailPacient}/{doctorID}", emailPacient, doctorID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(appointmentsService).getPacientAppointmentsForSpecialist(emailPacient, doctorID);
    }

    @Test
    @WithMockUser(username="user", roles={ "SPECIALIST", "PACIENT"})
    public void deleteAppointment() throws Exception {
        int id = 1;


        mockMvc.perform(patch("/appointment/delete/{id}", id)
                        .contentType("application/json")
                        .with(csrf())
                        .content(String.valueOf(Long.parseLong(objectMapper.writeValueAsString(id)))))
                .andExpect(status().isNoContent());

        verify(appointmentsService).delete(id);
    }




}
