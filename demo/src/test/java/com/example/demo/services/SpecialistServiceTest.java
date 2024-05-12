package com.example.demo.services;

import com.example.demo.controller.SpecialistController;
import com.example.demo.dtos.NewLocationDTO;
import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Authority;
import com.example.demo.entity.Location;
import com.example.demo.entity.Specialist;
import com.example.demo.entity.User;
import com.example.demo.exception.RegisterException;
import com.example.demo.repositories.LocationRepo;
import com.example.demo.repositories.SpecialistRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.AuthorityService.AuthorityService;
import com.example.demo.services.LocationsService.LocationService;
import com.example.demo.services.SpecialistService.SpecialistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SpecialistServiceTest {


    @InjectMocks
    private SpecialistService specialistService;

    @Mock
    private SpecialistRepo specialistRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private  LocationRepo locationRepo;

    @Mock
    private LocationService locationService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthorityService authorityService;

    @Test
    void whenEmailNotUsed_registerDoctor_savesSpecialist() {
        java.util.List<NewLocationDTO> locations = new ArrayList<>();
        locations.add(new NewLocationDTO(1L, "Location 1", "Address 1"));
        locations.add(new NewLocationDTO(2L, "Location 2", "Address 2"));
        RegisterSpecialistDTO dto = new RegisterSpecialistDTO("John", "Doe", "johndoe@example.com", "1234567890", "123 Street", "password", "Specialty", "Description", 100.0, "9AM-5PM",  locations);
        when(userRepo.getUserByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        Authority authority = new Authority("ROLE_SPECIALIST");
        when(authorityService.getAuthorityByName("ROLE_SPECIALIST")).thenReturn(authority); // Configure mock to return a dummy Authority

        // Act
        Specialist result = specialistService.registerDoctor(dto);

        // Assert
        assertNotNull(result);
        verify(userRepo).save(any(User.class));
        verify(specialistRepo).save(any(Specialist.class));
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userCaptor.capture());
        Assertions.assertEquals("encodedPassword", userCaptor.getValue().getPassword(), "Password should be encoded");

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
    public void whenSpecialistExists_getSpecialistByName_returnsListOfSpecialistDTO() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        java.util.List<SpecialistDTO> expectedList = new ArrayList<>();
        when(specialistRepo.getSpecialistsByname(firstName, lastName)).thenReturn(expectedList);

        // Act
        java.util.List<SpecialistDTO> result = specialistService.getSpecialistByName(firstName, lastName);

        // Assert
        assertNotNull(result);
        Assertions.assertEquals(expectedList, result);

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


    @Test
    public void getSpecialistList() throws Exception{
        SpecialistDTO entry1 = new SpecialistDTO(1, "ion", "daniel", "email", "071234567", "adresa", "medic", "descriere", 200.00, "marti");
        SpecialistDTO entry2 = new SpecialistDTO(2, "mircea", "dinu", "email", "071254567", "adresa2", "medic", "descriere", 200.00, "marti");

        List<SpecialistDTO> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);
        when(specialistRepo.getSpecialists()).thenReturn(list);

        java.util.List<SpecialistDTO> result = specialistService.getDoctorsList();

        Assertions.assertEquals(list, result); }
}
