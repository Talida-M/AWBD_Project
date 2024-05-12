package com.example.demo.services;

import antlr.collections.List;
import com.example.demo.dtos.DoctorPacientsDTO;
import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Authority;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.User;
import com.example.demo.exception.RegisterException;
import com.example.demo.repositories.PacientsRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.AuthorityService.AuthorityService;
import com.example.demo.services.PacientService.PacientService;
import com.example.demo.services.SpecialistService.SpecialistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("mysql")
public class PacientServiceTest {
    private MockMvc mockMvc;
    @InjectMocks
    private PacientService pacientService;

    @InjectMocks
    private SpecialistService specialistService;
    @Mock
    private PacientsRepo pacientsRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthorityService authorityService;
    @Test
    void whenEmailIsUnique_registerPacient_savesPacient() {
        RegisterPacientDTO dto = new RegisterPacientDTO("John", "Doe", "johndoe@example.com", "1234567890", "123 Street", "password", "Social Category", "username", true);
        when(userRepo.getUserByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        Authority authority = new Authority("ROLE_PACIENT");
        when(authorityService.getAuthorityByName("ROLE_PACIENT")).thenReturn(authority); // Configure mock to return a dummy Authority

        // Act
        pacientService.registerPacient(dto);

        // Assert
        verify(userRepo).save(any(User.class));
        verify(pacientsRepo).save(any(Pacient.class));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userCaptor.capture());
        assertEquals("encodedPassword", userCaptor.getValue().getPassword(), "Password should be encoded");
    }

    @Test
    void whenEmailAlreadyExists_registerPacient_throwsRegisterException() {
        // Arrange
        RegisterPacientDTO dto = new RegisterPacientDTO("Jane", "Doe", "janedoe@example.com", "9876543210", "321 Street", "password", "Social Category", "username", false);
        User existingUser = new User();
        existingUser.setEmail(dto.getEmail());
        when(userRepo.getUserByEmail(dto.getEmail())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(RegisterException.class, () -> pacientService.registerPacient(dto));
        verify(userRepo, times(0)).save(any(User.class));
        verify(pacientsRepo, times(0)).save(any(Pacient.class));
    }
    @Test
    void whenPacientExists_delete_marksPacientAndUserAsDeleted() {
        // Arrange
        Integer pacientId = 1;
        Pacient pacient = new Pacient();
        pacient.setPacientId(pacientId);
        User user = new User();
        user.setId(pacientId);
        when(pacientsRepo.findById(pacientId)).thenReturn(Optional.of(pacient));
        when(userRepo.findById(pacientId)).thenReturn(Optional.of(user));

        // Act
        pacientService.delete(pacientId);

        // Assert
        verify(pacientsRepo).delete(pacient);

}

    @Test
    public void getPacientsList() throws Exception{
        Integer id = 1;
        java.util.List<DoctorPacientsDTO> expectedList = new ArrayList<>();
        expectedList.add(new DoctorPacientsDTO("fff", "out", "oop", "0987654321", "Student", true, 1L));// fill in details
        when(pacientsRepo.getPacientsList(id)).thenReturn(expectedList);

        // Act
        java.util.List<DoctorPacientsDTO> result = pacientService.getPacientList(id);

        // Assert
        assertEquals(expectedList, result);
    }
}