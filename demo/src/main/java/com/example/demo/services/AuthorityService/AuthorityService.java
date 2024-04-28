package com.example.demo.services.AuthorityService;

import com.example.demo.entity.Authority;
import com.example.demo.repositories.AuthorityRepo;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements IAuthorityService{
    AuthorityRepo authorityRepository;
    public AuthorityService(AuthorityRepo authorityRepository){
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority getAuthorityByName(String authority){
        return authorityRepository.findByAuthority(authority);
    }
}
