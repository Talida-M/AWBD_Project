package com.example.demo.repositories;


import com.example.demo.dtos.DoctorPacientsDTO;
import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.entity.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacientsRepo extends  JpaRepository<Pacient,Integer> {
    @Query("SELECT DISTINCT NEW com.example.demo.dtos.DoctorPacientsDTO(u.firstName, u.lastName, u.email, u.phoneNumber,  p.socialCategory, p.hasInsurance, count(app.AppointmentId))" +
            " FROM Pacient p " +
            " JOIN User u ON u.id = p.user.id" +
            " JOIN Appointment  app ON p.pacientId = app.pacient.pacientId" +
            " where app.specialist.specialistId = :id  AND app.appointmentStatus = 'Programare_Realizata' ")
    List<DoctorPacientsDTO> getPacientsList(Integer id);
    @Query("SELECT DISTINCT pacient FROM Pacient pacient " +
            " JOIN User user on  user.id = pacient.user.id" +
            " where  user.email = :email")
    Optional<Pacient> getPacientByEmail(String email);

    @Query("SELECT DISTINCT pacient FROM Pacient pacient " +
            "WHERE pacient.username = :username")
    Optional<Pacient> getPacientByUsername(@Param("username") String username);
    @Query("SELECT   NEW com.example.demo.dtos.PacientDTO(pacient.pacientId, u.firstName, u.lastName, u.email, u.phoneNumber, u.address,  pacient.socialCategory, pacient.username, pacient.hasInsurance) FROM Pacient pacient " +
            "JOIN User u ON  u.id = pacient.user.id" )
    List<PacientDTO> getPacients();


}
