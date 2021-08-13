package com.example.CodeFellowship.web;

import com.example.CodeFellowship.domain.ApplicationUser;
import com.example.CodeFellowship.domain.Post;
import com.example.CodeFellowship.interfaces.ApplicationUserRepo;
import com.example.CodeFellowship.interfaces.PostRepo;
import com.example.CodeFellowship.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PostController {
    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserService userService;

    @PostMapping("/post/{id}")
    public RedirectView addPost(@RequestParam String body , @PathVariable Long id)throws  Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser current = applicationUserRepo.findApplicationUserByUsername(userDetails.getUsername());

        ApplicationUser applicationUser  = userService.findById(id);
        Long passedId = applicationUser.getId();
        boolean admin=applicationUser.getId().equals(current.getId());

        for (GrantedAuthority role : userDetails.getAuthorities()){
            if (role.toString().equals("ADMIN")|| applicationUser.getId().equals(current.getId())){
                admin = true;
                passedId = id;
            }
        }
        if (!id.equals(current.getId()) && !admin){
            throw new Exception(" you are not allowed to add post");
        }
        Post post = new Post(body , current);
        postRepo.save(post);
        return new RedirectView("/myprofile");
    }
}
