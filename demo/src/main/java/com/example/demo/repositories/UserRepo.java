package com.example.demo.repositories;


import com.example.demo.dtos.UserDTO;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    @Query("SELECT DISTINCT user FROM User user WHERE user.email = :email AND user.password = :password")
    Optional<User> getUser(String email, String password);

    @Query("SELECT DISTINCT user FROM User user WHERE user.email = :email")
    Optional<User> getUserByEmail(String email);
    @Query("SELECT NEW com.example.demo.dtos.UserDTO(user.id, user.firstName, user.lastName, user.email, user.phoneNumber, user.address, user.role) FROM User user")
    List<UserDTO> getUsers();
}
