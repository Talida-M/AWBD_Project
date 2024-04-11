package com.example.demo.services.SpecialistService;

import com.example.demo.dtos.NewLocationDTO;
import com.example.demo.dtos.RegisterSpecialistDTO;
import com.example.demo.dtos.SpecialistDTO;
import com.example.demo.entity.Location;
import com.example.demo.entity.Specialist;
import com.example.demo.entity.User;
import com.example.demo.exception.RegisterException;
import com.example.demo.repositories.LocationRepo;
import com.example.demo.repositories.SpecialistRepo;
import com.example.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SpecialistService implements ISpecialistService{
    @Autowired
    LocationRepo locationRepo;
    @Autowired
      SpecialistRepo specialistRepo;
    @Autowired
      UserRepo userRepo;

    public SpecialistService(SpecialistRepo specialistRepo, UserRepo userRepo, LocationRepo locationRepo) {
        this.specialistRepo = specialistRepo;
        this.userRepo = userRepo;
        this.locationRepo = locationRepo;
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
            newUser.setPassword(user.getPassword());
            newUser.setRole("Specialist");
            userRepo.save(newUser);

            Specialist newspecialist = new Specialist();
            newspecialist.setUser(newUser);
            newspecialist.setAppointmentTime(user.getAppointmentTime());
            newspecialist.setDescription(user.getDescription());
            newspecialist.setPrice(user.getPrice());
            newspecialist.setString(user.getString());
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

}
