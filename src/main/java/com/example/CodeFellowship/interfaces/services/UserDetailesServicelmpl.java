package com.example.CodeFellowship.interfaces.services;


import com.example.CodeFellowship.domain.ApplicationUser;
import com.example.CodeFellowship.interfaces.ApplicationUserRepo;
import com.example.CodeFellowship.interfaces.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailesServicelmpl implements UserDetailsService {

    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        ApplicationUser student = applicationUserRepo.findApplicationUserByUsername(username);

        if (student == null){
            System.out.println("username not found");
            throw new UsernameNotFoundException(username + " not found");
        }

        return new org.springframework.security.core.userdetails.User(
                student.getUsername(), student.getPassword(),student.isEnabled(), true , true, true, student.getAuthorities());

    }
}
