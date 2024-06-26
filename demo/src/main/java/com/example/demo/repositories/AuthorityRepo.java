package com.example.demo.repositories;

import com.example.demo.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepo extends JpaRepository<Authority, Integer> {
        Authority findByAuthority(String roleUser);
}
