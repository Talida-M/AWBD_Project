package com.example.demo.services.UserService;

import com.example.demo.dtos.LoginDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserUpdateDTO;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService implements IUserService{

    @Autowired
       UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    @Override
    public User update(Integer id, UserUpdateDTO updateUser) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found"));

        user.setAddress(updateUser.getAddress());
        user.setPhoneNumber(updateUser.getPhoneNumber());
        user.setLastName(updateUser.getLastName());

        return userRepo.save(user);
    }

    @Override
    public LoginDTO login(String email, String password) {
        User user = userRepo.getUser(email, password).orElseThrow(
                () -> new NoSuchElementException("User with this email and password not found"));

        return new LoginDTO(
                user.getId(),
                user.getEmail()
        );
    }
    @Transactional
    @Override
    public List<UserDTO> getUsers() {
        return userRepo.getUsers();
    }
}
