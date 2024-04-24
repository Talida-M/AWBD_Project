package com.example.demo.services.AppointmentsService;

import com.example.demo.dtos.AppointmentDTO;
import com.example.demo.dtos.NewAppointmentDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.Specialist;
import com.example.demo.entity.StatusAppointment;
import com.example.demo.repositories.AppointmentsRepo;
import com.example.demo.repositories.PacientsRepo;
import com.example.demo.repositories.SpecialistRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AppointmentsService implements  IAppointmentsService{
    ModelMapper modelMapper;
    @Autowired
    PacientsRepo pacientsRepo;
    @Autowired
    AppointmentsRepo appointmentsRepo;
    @Autowired
    SpecialistRepo specialistRepo;

    public AppointmentsService(SpecialistRepo specialistRepo, AppointmentsRepo appointmentsRepo, PacientsRepo pacientsRepo,ModelMapper modelMapper) {
        this.specialistRepo = specialistRepo;
        this.appointmentsRepo = appointmentsRepo;
        this.pacientsRepo     = pacientsRepo;
        this.modelMapper = modelMapper;
    }
    @Transactional
    @Override
    public void  newAppointment (NewAppointmentDTO app) {
        Pacient pacient = pacientsRepo.getPacientByEmail(app.getPacientEmail()).orElse(null);
        Specialist specialist = specialistRepo.getDoctorByEmail(app.getSpecialistEmail()).orElse(null);
            Appointment newapp = new Appointment();
            newapp.setAppointmentDate(app.getAppointmentDate());
            newapp.setAppointmentType(app.getAppointmentType());
            newapp.setAppointmentStatus(StatusAppointment.In_Asteptare.toString());
            newapp.setPacient(pacient);
            newapp.setSpecialist(specialist);
            appointmentsRepo.save(newapp);

    }

    @Override
    public void updateAppStatus(String status, Integer id) {
        Appointment app = appointmentsRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("Appointment does not exist"));
        app.setAppointmentStatus(status);
        appointmentsRepo.save(app);
    }

    @Override
    public List<Appointment> getAppointments() {
        return appointmentsRepo.getAppointments();
    }

    @Override
    public void delete(Integer id) {
        Appointment app = appointmentsRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("Appointment does not exist"));
        appointmentsRepo.delete(app);

    }
    @Transactional
    @Override
    public List<AppointmentDTO> getAppointmentsForPacientByStatus(String status, Integer pacientId) {
        return appointmentsRepo.getAppointmentsForPacientByStatus(status, pacientId);
    }

    @Transactional
    @Override
    public List<AppointmentDTO> getAppointmentsForDoctorByStatus(String status, Integer specialistID) {
        return appointmentsRepo.getAppointmentsForDoctorByStatus(status, specialistID);
    }

    @Transactional
    @Override
    public List<AppointmentDTO> getPacientAppointmentsForSpecialist(String emailPacient, Integer doctorID) {
        return appointmentsRepo.getAppointmentsForPacientByStatus(emailPacient, doctorID);
    }


    @Transactional
    @Override
    public Page<AppointmentDTO> getAppointmentsForDoctorByAppointmentStatus(String status, Integer specialistID, int page, int size) {
        Sort sortByAppointmentDate = Sort.by("appointmentDate").ascending();
        Pageable pageable = PageRequest.of(page, size, sortByAppointmentDate);
        return appointmentsRepo.getAppointmentsForDoctorByAppointmentStatus(status, specialistID, pageable);
    }

    @Transactional
    @Override
    public Page<AppointmentDTO> getAllDoctorAppointmentsForDoctorSortedByDate(int doctorId, int page, int size) {
        Sort sortByAppointmentDate = Sort.by("appointmentDate").ascending();
        Pageable pageable = PageRequest.of(page, size, sortByAppointmentDate);
        return appointmentsRepo.findAllBySpecialistSpecialistId( doctorId, pageable);
    }

    @Override
    public AppointmentDTO findById(Integer l) {
        Optional<Appointment> productOptional = appointmentsRepo.findById(l);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found!");
        }
        return modelMapper.map(productOptional.get(), AppointmentDTO.class);
    }

}
