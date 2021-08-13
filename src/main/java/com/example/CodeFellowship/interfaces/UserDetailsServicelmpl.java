package com.example.CodeFellowship.interfaces;

import com.example.CodeFellowship.domain.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServicelmpl implements  userDetailsService {
    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws  usernameNotFoundException{
        ApplicationUser student = applicationUserRepo.findApplicationUserByUsername(username);

        if (student == null){
            System.out.println("User name not found ");
            throw  new usernameNotFoundException((username + "not found "));
        }
        return student;
    }
}
