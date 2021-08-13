package com.example.CodeFellowship.interfaces.services;


import com.example.CodeFellowship.domain.Role;
import com.example.CodeFellowship.interfaces.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl extends SecurityService {

    @Autowired
    RoleRepo roleRepo;

    @Override
    public Role findRoleById(Long roleId){
        return roleRepo.findById(roleId).orElseThrow();
    }

    @Override
    public Role findRoleByName(String name){
        return roleRepo.findRoleByName(name).orElseThrow();
    }


}
