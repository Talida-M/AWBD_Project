package com.example.demo.services.SpecialistService;


import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Specialist;

import java.util.List;
import java.util.Optional;


public interface ISpecialistService {
    Specialist registerDoctor(RegisterSpecialistDTO user);
    List<SpecialistDTO> getDoctorsList();

    Optional<SpecialistDTO> getSpecialistByEmail(String email);

    List<SpecialistDTO> getSpecialistByName(String fname, String lname);
    void delete(Integer id);

}
