package com.example.demo.services;

import com.example.demo.dtos.NewAppointmentDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.Specialist;
import com.example.demo.repositories.AppointmentsRepo;
import com.example.demo.repositories.PacientsRepo;
import com.example.demo.repositories.SpecialistRepo;
import com.example.demo.services.AppointmentsService.AppointmentsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class AppointmentServiceTest {
    @InjectMocks
    private AppointmentsService appointmentsService;

    @Mock
    private PacientsRepo pacientsRepo;
    @Mock
    private AppointmentsRepo appointmentsRepo;
    @Mock
    private SpecialistRepo specialistRepo;


    @Test
    void newAppointment_createsNewAppointment() {
        // Arrange
        NewAppointmentDTO newAppointmentDTO = new NewAppointmentDTO("patient@example.com", "specialist@example.com", "Checkup", LocalDateTime.now());
        when(pacientsRepo.getPacientByEmail(newAppointmentDTO.getPacientEmail())).thenReturn(Optional.of(new Pacient()));
        when(specialistRepo.getDoctorByEmail(newAppointmentDTO.getSpecialistEmail())).thenReturn(Optional.of(new Specialist()));

        // Act
        appointmentsService.newAppointment(newAppointmentDTO);

        // Assert
        verify(appointmentsRepo).save(any(Appointment.class));
    }

    @Test
    void updateAppStatus_updatesAppointmentStatus() {
        // Arrange
        Integer appointmentId = 1;
        String newStatus = "Completed";
        Appointment existingAppointment = new Appointment();
        existingAppointment.setAppointmentId(appointmentId);
        when(appointmentsRepo.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));

        // Act
        appointmentsService.updateAppStatus(newStatus, appointmentId);

        // Assert
        assertEquals(newStatus, existingAppointment.getAppointmentStatus());
        verify(appointmentsRepo).save(existingAppointment);
    }

    @Test
    void getAppointments_returnsListOfAppointments() {
        // Arrange
        List<Appointment> expectedAppointments = Arrays.asList(new Appointment(), new Appointment());
        when(appointmentsRepo.getAppointments()).thenReturn(expectedAppointments);

        // Act
        List<Appointment> result = appointmentsService.getAppointments();

        // Assert
        assertNotNull(result);
        assertEquals(expectedAppointments.size(), result.size());
    }
    @Test
    void delete_deletesAppointment() {
        // Arrange
        Integer appointmentId = 1;
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);
        when(appointmentsRepo.findById(appointmentId)).thenReturn(Optional.of(appointment));

        // Act
        appointmentsService.delete(appointmentId);

        // Assert
        verify(appointmentsRepo).delete(appointment);
    }
    // Act
    // Assert
//      assertNotNull(result);


}
