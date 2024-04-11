package com.example.demo.services;

import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Specialist;
import com.example.demo.entity.User;
import com.example.demo.exception.RegisterException;
import com.example.demo.repositories.SpecialistRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.SpecialistService.SpecialistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)

public class SpecialistServiceTest {

    @InjectMocks
    private SpecialistService specialistService;

    @Mock
    private SpecialistRepo specialistRepo;

    @Mock
    private UserRepo userRepo;

    @Test
    void whenEmailNotUsed_registerDoctor_savesSpecialist() {
        // Arrange
        RegisterSpecialistDTO dto = new RegisterSpecialistDTO("John", "Doe", "johndoe@example.com", "1234567890", "123 Street", "password", "Specialty", "Description", 100.0, "9AM-5PM");
        when(userRepo.getUserByEmail(dto.getEmail())).thenReturn(Optional.empty());

        // Act
        Specialist result = specialistService.registerDoctor(dto);

        // Assert
        assertNotNull(result);
        verify(userRepo).save(any(User.class));
        verify(specialistRepo).save(any(Specialist.class));
    }

    @Test
    void whenEmailAlreadyExists_registerDoctor_throwsRegisterException() {
        // Arrange
        RegisterSpecialistDTO dto = new RegisterSpecialistDTO("Jane", "Doe", "janedoe@example.com", "9876543210", "321 Street", "password", "Specialty", "Description", 200.0, "10AM-6PM");
        User existingUser = new User();
        existingUser.setEmail(dto.getEmail());
        when(userRepo.getUserByEmail(dto.getEmail())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(RegisterException.class, () -> specialistService.registerDoctor(dto));
        verify(userRepo, times(0)).save(any(User.class));
        verify(specialistRepo, times(0)).save(any(Specialist.class));
    }

    @Test
    void whenSpecialistDoesNotExist_delete_throwsNoSuchElementException() {
        // Arrange
        Integer specialistId = 1;
        when(specialistRepo.findById(specialistId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> specialistService.delete(specialistId));
        verify(userRepo, times(0)).save(any(User.class));
    }

    @Test
    void whenSpecialistDoesNotExist_getSpecialistByEmail_returnsEmpty() {
        // Arrange
        String email = "nobody@example.com";
        when(specialistRepo.getSpecialistByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<SpecialistDTO> result = specialistService.getSpecialistByEmail(email);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void whenSpecialistExists_getSpecialistByName_returnsListOfSpecialistDTO() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        List<SpecialistDTO> expectedList = Arrays.asList(new SpecialistDTO());
        when(specialistRepo.getSpecialistsByname(firstName, lastName)).thenReturn(expectedList);

        // Act
        List<SpecialistDTO> result = specialistService.getSpecialistByName(firstName, lastName);

        // Assert
        assertNotNull(result);

    }

    @Test
    void whenSpecialistExists_delete_marksSpecialistAndUserAsDeleted() {
        // Arrange
        Integer specialistId = 1;
        Specialist specialist = new Specialist();
        specialist.setSpecialistId(specialistId);
        User user = new User();
        user.setId(specialistId);
        when(specialistRepo.findById(specialistId)).thenReturn(Optional.of(specialist));
        when(userRepo.findById(specialistId)).thenReturn(Optional.of(user));

        // Act
        specialistService.delete(specialistId);

        // Assert
        verify(specialistRepo).delete(specialist);

    }
}
