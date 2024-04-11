package com.example.demo.repositories;

import com.example.demo.dtos.LocationSpecialistsDTO;
import com.example.demo.dtos.NewLocationDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.entity.Location;
import com.example.demo.entity.Specialist;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepo extends JpaRepository<Location,Integer> {
    @Query("SELECT new com.example.demo.dtos.NewLocationDTO(loc.id, loc.name, loc.address) FROM Location loc " +
            "JOIN loc.specialists s  WHERE s.specialistId = :idS ")
    List<NewLocationDTO> getLocationBySpecialists(Integer idS);

    @Query("SELECT DISTINCT  new com.example.demo.dtos.SpecialistDTO(s.specialistId, u.firstName, u.lastName, u.email, u.phoneNumber,  s.specialty ) " +
            "FROM Specialist s " +
            "JOIN s.locations loc " +
            "JOIN s.user u " +
            "WHERE loc.id = :locationId  ")
    List<SpecialistDTO> getSpecialistsByLocation(long locationId);

    @Query("SELECT loc FROM Location loc " +
            " WHERE loc.name = :name ")
    Optional<Location> getByName(String name);


}
