package com.example.demo.controller;

import com.example.demo.dtos.AppointmentDTO;
import com.example.demo.dtos.NewAppointmentDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Authority;
import com.example.demo.entity.StatusAppointment;
import com.example.demo.entity.User;
import com.example.demo.services.AppointmentsService.IAppointmentsService;
import com.example.demo.services.AuthorityService.IAuthorityService;
import com.example.demo.services.PacientService.IPacientService;
import com.example.demo.services.SpecialistService.ISpecialistService;
import com.example.demo.services.UserService.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@Validated
@RequestMapping("appointment")
@Tag(name = "Appointment Controller", description = "Endpoints for managing appointments")
public class AppointmentController {

    @Autowired
    public IAppointmentsService appointmentsService;
    private IUserService userService;
    private  IPacientService pacientService;
    private ISpecialistService specialistService;

    @Autowired
    public AppointmentController(IAppointmentsService appointmentsService, IUserService userService, IPacientService pacientService, ISpecialistService specialistService) {
        this.appointmentsService = appointmentsService;
        this.userService = userService;
        this.pacientService = pacientService;
        this.specialistService = specialistService;
    }

    @Operation(summary = "Create a new appointment", responses = {
            @ApiResponse(responseCode = "200", description = "appointment has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/newApp")
    public ResponseEntity<Void> create(
            @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details", required = true) NewAppointmentDTO newA){
        appointmentsService.newAppointment(newA);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get Appointments For Pacients By Status", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAppPS/{status}/{pacientId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsForPacientByStatus(
            @PathVariable @Parameter(description = "status ", required = true) String status,
            @PathVariable @Parameter(description = "pacient ID", required = true) Integer pacientId){
        return ResponseEntity.ok(appointmentsService.getAppointmentsForPacientByStatus(status, pacientId));
    }


    @Operation(summary = "Get Appointments For Pacients By Status", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAppPD/{status}/{specialistID}")
    public ResponseEntity<Page<AppointmentDTO>> getAppointmentsForDoctorByStatus(
            @PathVariable @Parameter(description = "status", required = true) String status,
            @PathVariable @Parameter(description = "specialist ID", required = true) Integer specialistID,
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size") int size) {

        Page<AppointmentDTO> appointments = appointmentsService.getAppointmentsForDoctorByAppointmentStatus(status, specialistID, page, size);
        return ResponseEntity.ok(appointments);
    }

    @Operation(summary = "Get Appointments For Pacients By Status", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAppS/{emailPacient}/{doctorID}")
    public ResponseEntity<List<AppointmentDTO>> getPacientAppointmentsForSpecialist(
            @PathVariable @Parameter(description = "email pacient ", required = true) String emailPacient,
            @PathVariable @Parameter(description = "doctor ID", required = true) Integer doctorID){
        return ResponseEntity.ok(appointmentsService.getPacientAppointmentsForSpecialist(emailPacient, doctorID));
    }


    @Operation(summary = "Delete a user by ID.", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable @Parameter(description = "Appointment ID", required = true) Integer id){
        appointmentsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteAppointment(
            @PathVariable int id
    ){
        appointmentsService.delete(id);
        return new ModelAndView("redirect:/appointment/status-selection");
    }

    @Operation(summary = "Update  status appointment.", responses = {
            @ApiResponse(responseCode = "200", description = "status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/update/{status}/{id}")
    public ResponseEntity<Void> update(
            @PathVariable @Parameter(description = "status", required = true) String status,
            @PathVariable @Parameter(description = "ID", required = true) Integer id){
       appointmentsService.updateAppStatus(status, id);
        return ResponseEntity.ok().build();
    }
    @RequestMapping("/update/{status}/{id}")
    public ModelAndView updateAppointment(
            @PathVariable String status,
            @PathVariable int id
    ){
        appointmentsService.updateAppStatus(status, id);
        return new ModelAndView("redirect:/appointment/status-selection");
    }

    @Operation(summary = "Get Appointments For Pacients By Status", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getApps")
    public ResponseEntity<List<Appointment>> getAppointments(){
        return ResponseEntity.ok(appointmentsService.getAppointments());
    }

    @Operation(summary = "Get All Appointments For Doctor", responses = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Users not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAppD/{status}/{specialistID}")
    public ResponseEntity<Page<AppointmentDTO>> getAllAppointmentsForDoctor(
            @PathVariable @Parameter(description = "specialist ID", required = true) Integer specialistID,
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size") int size) {

        Page<AppointmentDTO> appointments = appointmentsService.getAllDoctorAppointmentsForDoctorSortedByDate( specialistID, page, size);
        return ResponseEntity.ok(appointments);


    }


    @GetMapping("/status-selection")
    public ModelAndView showStatusSelection(Model model) {
        List<String> statuses = Arrays.asList(StatusAppointment.In_Asteptare.toString(), StatusAppointment.Programare_Acceptata.toString(), StatusAppointment.Programare_Realizata.toString());
        model.addAttribute("statuses", statuses);
        return new ModelAndView("status-cards");
    }


    @RequestMapping("/form")
    public ModelAndView showpage(Model model){
        model.addAttribute("appointment",new NewAppointmentDTO());//mapper.requestAuthor(new RequestAuthor()));
        return new ModelAndView("appointmentForm");
    }

    @PostMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute("appointment") NewAppointmentDTO appointment) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Received appointment: {}", appointment);

        appointmentsService.newAppointment(appointment);
        return new ModelAndView("redirect:/");
    }




    @GetMapping("/appointmentList/{status}")
    public ModelAndView getAppointmentsByStatus(@PathVariable String status, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_PACIENT"))) {
            Integer userId = userService.getUserByEmail(email);
            Integer pacientId = pacientService.getPacientByUserId(userId);
            Page<AppointmentDTO> appointments = appointmentsService.getAppointmentsForPacientByStatusPage(status, pacientId, 0, 5);
            model.addAttribute("appointments", appointments);
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_SPECIALIST"))) {
            Integer userId = userService.getUserByEmail(email);
            Integer specialistId = specialistService.getSpecialistByUserId(userId);
            Page<AppointmentDTO> appointments = appointmentsService.getAppointmentsForDoctorByAppointmentStatus(status, specialistId, 0, 5);
            model.addAttribute("appointments", appointments);
        }

        return new ModelAndView("appointmentList" );
    }





}
