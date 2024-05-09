package com.example.demo.controller;

import antlr.collections.List;
import com.example.demo.dtos.AppointmentDTO;
import com.example.demo.dtos.NewAppointmentDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.services.AppointmentsService.IAppointmentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = AppointmentController.class)
@ActiveProfiles("h2")
@AutoConfigureTestDatabase
@SpringJUnitWebConfig
public class AppointmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAppointmentsService appointmentsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createAppointment() throws Exception {
        NewAppointmentDTO newAppointmentDTO = new NewAppointmentDTO("patient@example.com", "specialist@example.com", "Checkup", LocalDateTime.now());
        mockMvc.perform((RequestBuilder) post("/appointment/newApp")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newAppointmentDTO)))
                .andExpect(status().isOk());
        verify(appointmentsService).newAppointment(any(NewAppointmentDTO.class));
    }

    @Test
    public void getAppointmentsForPacientByStatus() throws Exception {
        String status = "booked"; // Specify a valid status
        int pacientId = 1; // Specify a valid pacientId

        List<AppointmentDTO> pacientList = Arrays.asList(new AppointmentDTO(1, "O", LocalDateTime.now(), "ff", "jj", "jjj"));
        when(appointmentsService.getAppointmentsForPacientByStatus(status, pacientId)).thenReturn(pacientList);
        mockMvc.perform(get("/specialist/getSpecialistByName/John/Doe")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(appointmentsService).getAppointmentsForPacientByStatus(status, pacientId);
    }

    @Test
    public void getAppointmentsForDoctorByStatus() throws Exception {
        String status = "confirmed"; // Specify a valid status
        int specialistID = 1; // Specify a valid specialistID


        List<AppointmentDTO> pacientList = Arrays.asList(new AppointmentDTO(1, "O", LocalDateTime.now(), "ff", "jj", "jjj"));
        when(appointmentsService.getAppointmentsForPacientByStatus(status, specialistID)).thenReturn(pacientList);
        mockMvc.perform(get("/appointment/getAppPD/{status}/{specialistID}", status, specialistID)

                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(appointmentsService).getAppointmentsForDoctorByStatus(status, specialistID);
    }

    @Test
    public void getPacientAppointmentsForSpecialist() throws Exception {
        String emailPacient = "example@example.com"; // Specify a valid emailPacient
        int doctorID = 1; // Specify a valid doctorID


        List<AppointmentDTO> pacientList = Arrays.asList(new AppointmentDTO(1, "O", LocalDateTime.now(), "ff", "jj", "jjj"));
        when(appointmentsService.getAppointmentsForPacientByStatus(emailPacient, doctorID)).thenReturn(pacientList);
        mockMvc.perform(get("/appointment/getAppS/{emailPacient}/{doctorID}", emailPacient, doctorID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pacientList)))
                .andExpect(status().isOk());
        verify(appointmentsService).getPacientAppointmentsForSpecialist(emailPacient, doctorID);
    }

    @Test
    public void deleteAppointment() throws Exception {
        int id = 1; // Specify a valid id for the appointment to delete


        mockMvc.perform(patch("/appointment/delete/{id}", id)
                        .contentType("application/json")
                        .content(String.valueOf(Long.parseLong(objectMapper.writeValueAsString(id)))))
                .andExpect(status().isNoContent());

        verify(appointmentsService).delete(id);
    }


    @Test
    public void getAppointments() throws Exception{
        Appointment appointment = new Appointment(1,1,2,'online',new LocalDateTime(),'in asteptare');
        Appointment appointment2 = new Appointment(2,3,2,'fizic',new LocalDateTime(),'respinsa');

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        appointments.add(appointment2);
        when(appointmentsService.getAppointments()).thenReturn(appointments);

        mockMvc.perform(get("/appointment"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointmentList"))
                .andExpect(model().attribute("appointments",appointments));
    }

}
