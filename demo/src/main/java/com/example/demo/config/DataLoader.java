package com.example.demo.config;

import com.example.demo.entity.Authority;
import com.example.demo.entity.User;
import com.example.demo.repositories.AuthorityRepo;
import com.example.demo.repositories.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("mysql")
public class DataLoader implements CommandLineRunner {

    private AuthorityRepo authorityRepo;
    private UserRepo userRepository;
    private PasswordEncoder passwordEncoder;

    public DataLoader(AuthorityRepo authorityRepo, UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepo = authorityRepo;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void loadUserData() {
        if (userRepository.count() == 0) {
            Authority authority_admin = new Authority("ROLE_ADMIN");
            authorityRepo.save(authority_admin);

//            Authority authority_user = new Authority("ROLE_DOCTOR");
//            authorityRepo.save(authority_user);

            User admin = new User();
            admin.setEmail("admin@yahoo.com");
            admin.setPassword(passwordEncoder.encode("pass"));
            admin.setFirstName("admin_firstname");
            admin.setLastName("admin_lastname");
            admin.setAuthority(authority_admin);
            admin.setAddress("admin_address");
            admin.setPhoneNumber("admin_phone");
            userRepository.save(admin);


        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}