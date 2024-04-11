package com.example.demo.services;

import com.example.demo.dtos.JournalPagesDTO;
import com.example.demo.dtos.NewPageDTO;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.UserPageJournal;
import com.example.demo.repositories.PacientsRepo;
import com.example.demo.repositories.UserJournalRepo;
import com.example.demo.services.JournalService.JournalPageService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JournalServiceTest {
    @InjectMocks
    private JournalPageService journalPageService;

    @Mock
    private UserJournalRepo journalRepo;

    @Mock
    private PacientsRepo pacientsRepo;
    @Test
    void whenPacientExists_createPage_savesNewPage() {
        // Arrange
        NewPageDTO newPageDTO = new NewPageDTO(LocalDateTime.now(), "pacient@example.com", "Content", true);
        Pacient existingPacient = new Pacient(); // assuming Pacient is a valid entity
        when(pacientsRepo.getPacientByEmail(newPageDTO.getPacientEmail())).thenReturn(Optional.of(existingPacient));

        // Act
        journalPageService.createPage(newPageDTO);

        // Assert
        verify(journalRepo).save(any(UserPageJournal.class));
    }
    @Test
    void whenPageExists_changestatus_updatesStatus() {
        // Arrange
        int pageId = 1;
        boolean newStatus = true;
        UserPageJournal existingPage = new UserPageJournal();
        existingPage.setPageId(pageId);
        when(journalRepo.findById(pageId)).thenReturn(Optional.of(existingPage));

        // Act
        journalPageService.changestatus(newStatus, pageId);
        ArgumentCaptor<UserPageJournal> pageCaptor = ArgumentCaptor.forClass(UserPageJournal.class);

        // Assert
        verify(journalRepo).save(pageCaptor.capture());
//        assertEquals(newStatus, pageCaptor.getValue().isPublic());
    }
    @Test
    void getPacientJournalPagesBySpecialist_returnsListOfJournalPagesDTO() {
        // Arrange
        Integer pacientId = 1;
        List<JournalPagesDTO> expectedList = Arrays.asList(new JournalPagesDTO());
        when(journalRepo.getPacientJournalPagesBySpecialist(pacientId)).thenReturn(expectedList);

        // Act
        List<JournalPagesDTO> result = journalPageService.getPacientJournalPagesBySpecialist(pacientId);

        // Assert
        assertNotNull(result);
//        assertEquals(expectedList.size(), result.size());
    }

    @Test
    void getUserJournalPages_returnsListOfJournalPagesDTO() {
        // Arrange
        Integer pacientId = 1;
        List<JournalPagesDTO> expectedList = Arrays.asList(new JournalPagesDTO());
        when(journalRepo.getUserJournalPages(pacientId)).thenReturn(expectedList);

        // Act
        List<JournalPagesDTO> result = journalPageService.getUserJournalPages(pacientId);

        // Assert
        assertNotNull(result);
//        assertEquals(expectedList.size(), result.size());
    }


}
