package com.example.demo.services.UserService;

import com.example.demo.dtos.LoginDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserUpdateDTO;
import com.example.demo.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface IUserService {
//    UserResponse login(String email, String password);
    List<UserDTO> getUsers();
    LoginDTO login(String email, String password);

    @Transactional
    User update(Integer id, UserUpdateDTO updateUser);
}
