package com.example.demo.services.JournalService;


import com.example.demo.dtos.JournalPagesDTO;
import com.example.demo.dtos.NewPageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IJournalPageService {
    void createPage (NewPageDTO journalPagesDTO);
    void changestatus (boolean status, int pageId);
    List<JournalPagesDTO> getPacientJournalPagesBySpecialist(Integer pacientId);
    List<JournalPagesDTO> getUserJournalPages(Integer pacientId);

}
