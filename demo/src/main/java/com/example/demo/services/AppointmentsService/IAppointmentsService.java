package com.example.demo.services.AppointmentsService;


import com.example.demo.dtos.AppointmentDTO;
import com.example.demo.dtos.NewAppointmentDTO;
import com.example.demo.entity.Appointment;

import java.util.List;


public interface IAppointmentsService {

      void  newAppointment (NewAppointmentDTO newAppointment);
      void updateAppStatus(String status, Integer id);
    List<Appointment> getAppointments();


    void delete(Integer id);
    List<AppointmentDTO> getAppointmentsForPacientByStatus(String status, Integer pacientId);
    List<AppointmentDTO> getAppointmentsForDoctorByStatus(String status, Integer specialistID);
    List<AppointmentDTO> getPacientAppointmentsForSpecialist(String emailPacient, Integer doctorID);


}
