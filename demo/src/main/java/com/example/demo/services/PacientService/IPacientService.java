package com.example.demo.services.PacientService;

import com.example.demo.dtos.DoctorPacientsDTO;
import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.entity.Pacient;

import java.util.List;


public interface IPacientService {
     void registerPacient(RegisterPacientDTO user);
     List<DoctorPacientsDTO> getPacientList(Integer id);
     List<PacientDTO> getPacients();
     void delete(Integer id);
}
