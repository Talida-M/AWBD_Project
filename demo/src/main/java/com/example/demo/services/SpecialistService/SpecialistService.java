package com.example.demo.services.SpecialistService;

import com.example.demo.dtos.NewLocationDTO;
import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Authority;
import com.example.demo.entity.Location;
import com.example.demo.entity.Specialist;
import com.example.demo.entity.User;
import com.example.demo.exception.RegisterException;
import com.example.demo.repositories.LocationRepo;
import com.example.demo.repositories.SpecialistRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.AuthorityService.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpecialistService implements ISpecialistService{
    ModelMapper modelMapper;

    @Autowired
    LocationRepo locationRepo;
    @Autowired
      SpecialistRepo specialistRepo;
    @Autowired
      UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    private IAuthorityService authorityService;

    public SpecialistService(SpecialistRepo specialistRepo, UserRepo userRepo, LocationRepo locationRepo,ModelMapper modelMapper, IAuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.specialistRepo = specialistRepo;
        this.userRepo = userRepo;
        this.locationRepo = locationRepo;
        this.modelMapper = modelMapper;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Integer getSpecialistByUserId(Integer id) {
        return specialistRepo.getSpecialistId(id).orElseThrow(
                () -> new NoSuchElementException("User with this email not found")).getSpecialistId();
    }
    @Transactional
    @Override
    public Specialist registerDoctor(RegisterSpecialistDTO user) {
        User verifyEmail = userRepo.getUserByEmail(user.getEmail()).orElse(null);

        if (verifyEmail == null) {
            User newUser = new User();
            newUser.setLastName(user.getLastName());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setAddress(user.getAddress());
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setRole("SPECIALIST");
            Authority authority = authorityService.getAuthorityByName("ROLE_SPECIALIST");
            newUser.setAuthority(authority);
            userRepo.save(newUser);

            Specialist newspecialist = new Specialist();
            newspecialist.setUser(newUser);
            newspecialist.setAppointmentTime(user.getAppointmentTime());
            newspecialist.setDescription(user.getDescription());
            newspecialist.setPrice(user.getPrice());
            newspecialist.setString(user.getSpecialty());
            specialistRepo.save(newspecialist);

            List<Location> locations = new ArrayList<>();
            for (NewLocationDTO locationDTO : user.getLocations()) {
                Location location = new Location();
                location.setAdresa(locationDTO.getAddress());
                location.setName(locationDTO.getName());
                location.getSpecialists().add(newspecialist);
                locationRepo.save(location);
                locations.add(location);
            }

            return  newspecialist;
        }
        else {
            throw new RegisterException("The email is already used by other user");
        }
    }
    @Override
    public void delete(Integer id) {
        Specialist specialist = specialistRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found"));
        specialistRepo.delete(specialist);
        User user = userRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found"));

        user.setDeleted(true);
        userRepo.save(user);
    }

    @Transactional
    @Override
    public List<SpecialistDTO> getDoctorsList () {
        return specialistRepo.getSpecialists();
    }
    @Transactional
    @Override
    public Optional<SpecialistDTO> getSpecialistByEmail(String email) {
        return specialistRepo.getSpecialistByEmail(email);
    }

    @Transactional
    @Override
    public List<SpecialistDTO> getSpecialistByName (String fname, String lname) {
        return specialistRepo.getSpecialistsByname(fname, lname);
    }

    @Override
    public List<SpecialistDTO> findAll(){

        List<Specialist> specialists = new LinkedList<>();
        specialistRepo.findAll(Sort.by("firstName")
        ).iterator().forEachRemaining(specialists::add);

        return specialists.stream()
                .map(specialist -> modelMapper.map(specialist, SpecialistDTO.class))
                .collect(Collectors.toList());
    }

}
