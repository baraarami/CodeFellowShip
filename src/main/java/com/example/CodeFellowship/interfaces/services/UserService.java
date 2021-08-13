package com.example.CodeFellowship.interfaces.services;

import com.example.CodeFellowship.domain.ApplicationUser;

import java.util.List;

public interface UserService {
    ApplicationUser findApplicationUserByUsername(String username);
    List<ApplicationUser> findAll();

    ApplicationUser findById(Long id);
    void save(ApplicationUser applicationUser);
}
