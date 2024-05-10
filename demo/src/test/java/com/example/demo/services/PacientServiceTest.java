package com.example.demo.services;

import antlr.collections.List;
import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.entity.Appointment;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    public void getPacientsList() throws Exception{
        PacientDTO entry1 = new PacientDTO(1,"ion","daniel","email","071234567","adresa", "student", "iond",true);
        PacientDTO entry2 = new PacientDTO(2,"mircea","dinu","email","071254567","adresa2", "student", "mircead",true);

        List<PacientDTO> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);
        when(pacientService.getPacients()).thenReturn(list);

        mockMvc.perform(get("/pacient"))
                .andExpect(status().isOk())
                .andExpect(view().name("pacientList"))
                .andExpect(model().attribute("pacients",list));
    }
}