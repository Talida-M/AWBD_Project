package com.example.demo.services.PacientService;

import com.example.demo.dtos.DoctorPacientsDTO;
import com.example.demo.dtos.PacientDTO;
import com.example.demo.dtos.RegisterPacientDTO;
import com.example.demo.entity.Authority;
import com.example.demo.entity.Pacient;
import com.example.demo.entity.User;
import com.example.demo.exception.RegisterException;
import com.example.demo.repositories.PacientsRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.AuthorityService.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class PacientService implements IPacientService{

    @Autowired
    private final PacientsRepo pacientsRepo;

    @Autowired
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final IAuthorityService authorityService;

    public PacientService(PacientsRepo pacientsRepo, UserRepo userRepo, IAuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.pacientsRepo = pacientsRepo;
        this.userRepo = userRepo;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void  registerPacient(RegisterPacientDTO user) {
        User verifyEmail = userRepo.getUserByEmail(user.getEmail()).orElse(null);

        if (verifyEmail == null) {
            User newUser = new User();
            newUser.setLastName(user.getLastName());
            newUser.setPhoneNumber(user.getPhoneNumber());
            newUser.setAddress(user.getAddress());
            newUser.setEmail(user.getEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setRole("PACIENT");
            Authority authority = authorityService.getAuthorityByName("ROLE_PACIENT");
            newUser.setAuthority(authority);
            userRepo.save(newUser);

            Pacient newpacient = new Pacient();
            newpacient.setUser(newUser);
            newpacient.setHasInsurance(user.isHasInsurance());
            newpacient.setSocialCategory(user.getSocialCategory());
            newpacient.setUsername(user.getUsername());
            pacientsRepo.save(newpacient);

        }
        else {
            throw new RegisterException("The email is already used by other user");
        }
    }

    @Transactional
    @Override
    public List<DoctorPacientsDTO> getPacientList (Integer id) {
       return pacientsRepo.getPacientsList(id);

    }

    @Override
    public List<PacientDTO> getPacients() {
        return pacientsRepo.getPacients();
    }

    @Override
    public void delete(Integer id) {
        Pacient pacient = pacientsRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found"));
        pacientsRepo.delete(pacient);
        User user = userRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found"));

        user.setDeleted(true);
        userRepo.save(user);
    }

    @Override
    public Integer getPacientByUserId(Integer id) {
        return pacientsRepo.getPacientId(id).orElseThrow(
                () -> new NoSuchElementException("User with this email not found")).getPacientId();
    }
}
