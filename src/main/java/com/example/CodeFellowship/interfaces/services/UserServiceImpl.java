package com.example.CodeFellowship.interfaces.services;

import com.example.CodeFellowship.domain.ApplicationUser;
import com.example.CodeFellowship.interfaces.ApplicationUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Override
    public ApplicationUser findApplicationUserByUsername(String username){
        return applicationUserRepo.findApplicationUserByUsername(username);

    }


    @Override
    public List<ApplicationUser> findAll(){
        return applicationUserRepo.findAll();
    }

    @Override
    public ApplicationUser findBuId(Long id){
        return applicationUserRepo.findById(id).orElseThrow();
    }

    @Override
    public void save(ApplicationUser applicationUser){
        applicationUserRepo.save(applicationUser);
    }
}
