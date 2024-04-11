package com.example.demo.repositories;

import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialistRepo extends JpaRepository<Specialist, Integer> {
    @Query("SELECT  NEW com.example.demo.dtos.SpecialistDTO(u.id, u.firstName, u.lastName, u.email, u.phoneNumber, u.address,  a.specialty, a.description, a.price, a.appointmentTime)" +
            " FROM Specialist a " +
            " JOIN User u ON  u.id = a.user.id ")
    List<SpecialistDTO> getSpecialists();

    @Query("SELECT NEW com.example.demo.dtos.SpecialistDTO(u.id, u.firstName, u.lastName, u.email, u.phoneNumber, u.address,  a.specialty, a.description, a.price, a.appointmentTime) " +
            "FROM Specialist a JOIN User u ON  u.id = a.user.id where (u.email) like LOWER(:email) ")
    Optional<SpecialistDTO> getSpecialistByEmail(String email);
//    @Query("SELECT  NEW com.example.demo.dtos.SpecialistDTO(u.id, u.firstName, u.lastName, u.email, u.phoneNumber, u.address,  a.specialty, a.description, a.price, a.appointmentTime) " +
//            "FROM Specialist a " +
//            "JOIN User u ON  u.id = a.specialistId  WHERE LOWER(u.firstName) like LOWER(:firstName) AND LOWER(u.lastName) like LOWER(:lastname) ")
//    List<SpecialistDTO> getSpecialistsByname(String firstname, String lastname);
@Query("SELECT NEW com.example.demo.dtos.SpecialistDTO(u.id, u.firstName, u.lastName, u.email, u.phoneNumber, u.address, a.specialty, a.description, a.price, a.appointmentTime) " +
        "FROM Specialist a " +
        "JOIN User u ON u.id = a.user.id  " +
        "WHERE LOWER(u.firstName) LIKE LOWER(:firstname) AND LOWER(u.lastName) LIKE LOWER(:lastname)")
List<SpecialistDTO> getSpecialistsByname(@Param("firstname") String firstname, @Param("lastname") String lastname);

    @Query("SELECT DISTINCT sp FROM Specialist sp " +
            " JOIN User user on  user.id = sp.user.id " +
            " where  LOWER(user.email) LIKE LOWER(:email) ")
    Optional<Specialist> getDoctorByEmail(String email);
}
