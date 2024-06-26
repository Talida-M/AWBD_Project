package com.example.demo.repositories;


import com.example.demo.dtos.AppointmentDTO;
import com.example.demo.entity.Appointment;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentsRepo extends JpaRepository<Appointment,Integer> {
    @Query("SELECT  NEW com.example.demo.dtos.AppointmentDTO( app.AppointmentId, app.appointmentType, app.appointmentDate, app.appointmentStatus, p.lastName || ' ' || p.firstName, p.email)" +
            " FROM Appointment app " +
            " JOIN User p ON p.id = app.pacient.user.id" +
            " where app.appointmentStatus = :status and  app.pacient.pacientId = :pacientId " +
            "order by app.appointmentDate DESC ")
    List<AppointmentDTO> getAppointmentsForPacientByStatus(String status, Integer pacientId);

    @Query("SELECT  NEW com.example.demo.dtos.AppointmentDTO(app.AppointmentId, app.appointmentType, app.appointmentDate, app.appointmentStatus, p.lastName || ' ' || p.firstName, p.email)" +
            " FROM Appointment app " +
            " JOIN User p ON p.id = app.specialist.user.id" +
            " where app.appointmentStatus = :status and app.specialist.specialistId = :specialistID " +
            " order by app.appointmentDate DESC ")
    List<AppointmentDTO> getAppointmentsForDoctorByStatus(String status, Integer specialistID);

//    @Query("SELECT  NEW com.example.demo.dtos.AppointmentDTO(app.AppointmentId, app.appointmentType, app.appointmentDate, app.appointmentStatus, p.lastName || ' ' || p.firstName, p.email)" +
//            " FROM Appointment app " +
//            " JOIN User p ON p.id = app.pacient.user.id" +
//            " where   app.specialist.specialistId = :doctorID and p.email = :emailPacient " +
//            " order by app.appointmentDate DESC ")
//    List<AppointmentDTO> getPacientAppointmentsForSpecialist(String emailPacient, Integer doctorID);

    @Query("SELECT  app" +
            " FROM Appointment app " +
             " order by app.appointmentDate DESC ")
    List<Appointment> getAppointments();

    @Query("SELECT  app" +
            " FROM Appointment app " +
            "where app.AppointmentId = :id" +
            " order by app.appointmentDate DESC ")
    Appointment getAppointmentById(Integer id);

    @Query("SELECT  NEW com.example.demo.dtos.AppointmentDTO(app.AppointmentId, app.appointmentType, app.appointmentDate, app.appointmentStatus, p.lastName || ' ' || p.firstName, p.email, app.specialist.user.lastName || ' ' ||app.specialist.user.firstName, app.specialist.user.email  )" +
            " FROM Appointment app " +
            " JOIN User p ON p.id = app.pacient.user.id" +
            " where app.appointmentStatus = :status and app.specialist.specialistId = :specialistID " +
            " order by app.appointmentDate DESC ")
    Page<AppointmentDTO> getAppointmentsForDoctorByAppointmentStatus(String status, Integer specialistID, Pageable pageable);
    @Query("SELECT  NEW com.example.demo.dtos.AppointmentDTO(app.AppointmentId, app.appointmentType, app.appointmentDate, app.appointmentStatus,  app.pacient.user.lastName || ' ' ||app.pacient.user.firstName, app.pacient.user.email,  p.lastName || ' ' || p.firstName, p.email )" +
            " FROM Appointment app " +
            " JOIN User p ON p.id = app.specialist.user.id" +
            " where app.appointmentStatus = :status and app.pacient.pacientId = :pacientId " +
            " order by app.appointmentDate DESC ")
    Page<AppointmentDTO> getAppointmentsForPacientByStatusPage(String status, Integer pacientId, Pageable pageable);

    Page<AppointmentDTO> findAllBySpecialistSpecialistId(Integer doctorId, Pageable pageable);


}
