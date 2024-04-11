package com.example.demo.services;

import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.User;
import com.example.demo.exception.RegisterException;
import com.example.demo.repositories.PacientsRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.PacientService.PacientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacientServiceTest {
    @InjectMocks
    private PacientService pacientService;

    @Mock
    private PacientsRepo pacientsRepo;

    @Mock
    private UserRepo userRepo;

    @Test
    void whenEmailIsUnique_registerPacient_savesPacient() {
        // Arrange
        RegisterPacientDTO dto = new RegisterPacientDTO("John", "Doe", "johndoe@example.com", "1234567890", "123 Street", "password", "Social Category", "username", true);
        when(userRepo.getUserByEmail(dto.getEmail())).thenReturn(Optional.empty());

        // Act
        pacientService.registerPacient(dto);

        // Assert
       verify(userRepo).save(any(User.class));
        verify(pacientsRepo).save(any(Pacient.class));
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
}