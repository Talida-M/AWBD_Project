package com.example.demo.services.JournalService;

import com.example.demo.dtos.JournalPagesDTO;
import com.example.demo.dtos.NewPageDTO;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.UserPageJournal;
import com.example.demo.repositories.PacientsRepo;
import com.example.demo.repositories.UserJournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class JournalPageService implements IJournalPageService{
    @Autowired
     UserJournalRepo userJournalRepo;
    @Autowired
     PacientsRepo pacientsRepo;

    public JournalPageService(UserJournalRepo userJournalRepo, PacientsRepo pacientsRepo) {
        this.userJournalRepo = userJournalRepo;
        this.pacientsRepo = pacientsRepo;
    }



    @Transactional
    @Override
    public void createPage(NewPageDTO journalPagesDTO) {
        Pacient pacient = pacientsRepo.getPacientByEmail(journalPagesDTO.getPacientEmail()).orElse(null);
        UserPageJournal newapp = new UserPageJournal();
        newapp.setPageDate(journalPagesDTO.getPageDate());
        newapp.setContent(journalPagesDTO.getContent());
        newapp.setPublic(journalPagesDTO.isPublic());
        newapp.setPacient(pacient);
        userJournalRepo.save(newapp);
    }

    @Override
    public void changestatus(boolean status, int pageId) {
        UserPageJournal app = userJournalRepo.findById(pageId).orElseThrow(
                () -> new NoSuchElementException("Page does not exist"));
        app.setPublic(status);
        userJournalRepo.save(app);
    }

    @Override
    public List<JournalPagesDTO> getPacientJournalPagesBySpecialist(Integer pacientId) {
        return userJournalRepo.getPacientJournalPagesBySpecialist(pacientId);
    }

    @Override
    public List<JournalPagesDTO> getUserJournalPages(Integer pacientId) {
        return userJournalRepo.getUserJournalPages(pacientId);
    }
}
