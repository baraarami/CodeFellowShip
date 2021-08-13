package com.example.CodeFellowship.web;


import com.example.CodeFellowship.domain.ApplicationUser;
import com.example.CodeFellowship.interfaces.ApplicationUserRepo;
import com.example.CodeFellowship.interfaces.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class AuthController {
    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    SecurityService securityService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/login")
    public String loginRoute(){
        return "login.html";
    }

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }

    @PostMapping("/signup")
    public RedirectView attemptSignUp(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String firstName,
                                      @RequestParam String lastName,
                                      @RequestParam String bio,
                                      @RequestParam String dateOfBirth)throws ParseException{
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter2.parse(dateOfBirth);
        System.out.println(date + "----------------------------------------------------------------------------");
        ApplicationUser newApplicationUser = new ApplicationUser(encoder.encode(password) , username, firstName, lastName ,bio ,date);
       newApplicationUser.setRole(securityService.findRoleByName("USER"));
        newApplicationUser = applicationUserRepo.save(newApplicationUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(newApplicationUser , null , new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  new RedirectView("/myprofile");
    }

}
