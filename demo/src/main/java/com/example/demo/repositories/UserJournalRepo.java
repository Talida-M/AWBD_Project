package com.example.demo.repositories;

import com.example.demo.dtos.JournalPagesDTO;
import com.example.demo.entity.UserPageJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJournalRepo extends JpaRepository<UserPageJournal,Integer> {
    @Query("SELECT  NEW com.example.demo.dtos.JournalPagesDTO(pj.pageId, pj.pageDate, p.lastName || ' ' || p.firstName, pj.content, pj.isPublic)" +
            " FROM UserPageJournal pj " +
            " JOIN User p ON p.id = pj.pacient.user.id" +
            " where  pj.pacient.pacientId = :pacientId " +
            "order by pj.pageDate DESC ")
    List<JournalPagesDTO> getUserJournalPages(Integer pacientId);

    @Query("SELECT  NEW com.example.demo.dtos.JournalPagesDTO(pj.pageId, pj.pageDate, p.lastName || ' ' || p.firstName, pj.content, pj.isPublic)" +
            " FROM UserPageJournal pj " +
            " JOIN User p ON p.id = pj.pacient.user.id" +
            " where  pj.pacient.pacientId = :pacientId and pj.isPublic = true " +
            "order by pj.pageDate DESC ")
    List<JournalPagesDTO> getPacientJournalPagesBySpecialist(Integer pacientId);

}
