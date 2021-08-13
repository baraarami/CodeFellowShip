package com.example.CodeFellowship.web;


import com.example.CodeFellowship.domain.ApplicationUser;
import com.example.CodeFellowship.domain.Post;
import com.example.CodeFellowship.interfaces.ApplicationUserRepo;
import com.example.CodeFellowship.interfaces.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    ApplicationUserRepo applicationUserRepo;

    @Autowired
    PostRepo postRepo;

    @GetMapping("/profile")
    public String getProfilepage(Model model){
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ApplicationUser applicationUser = applicationUserRepo.findApplicationUserByUsername(userDetails.getUsername());

        return profileDate(model , applicationUser , true);
    }
    private String profileDate (Model model , ApplicationUser applicationUser
     , boolean showPostForm){
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = formatter2.format(applicationUser.getDataOfBirth());
        List<Post> posts = applicationUser.getPosts();

        model.addAttribute("username" , applicationUser.getUsername());
        model.addAttribute("firstName" , applicationUser.getFirstName());
        model.addAttribute("lastName" , applicationUser.getLastName());
        model.addAttribute("date" , formattedDate);
        model.addAttribute("bio" , applicationUser.getBio());
        model.addAttribute("showPostForm" , showPostForm);
        model.addAttribute("posts" , posts);
        model.addAttribute("id" , applicationUser.getId());


        return "profile";

    }


    @GetMapping("users")
    public String getAllUsers(Model model){
        List<ApplicationUser> users = applicationUserRepo.findAll();
        model.addAttribute("users " , users);

        return "users";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable Long id , Model model){
        ApplicationUser applicationUser= applicationUserRepo.findById(id).orElseThrow();

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser = applicationUserRepo.findApplicationUserByUsername(userDetails.getUsername());

        boolean showPostForm;
        showPostForm= applicationUser.getId().equals(currentUser.getId());
        return profileDate(model , applicationUser , showPostForm);


    }


@GetMapping("/posts/{id}")
    public RedirectView addPost(@RequestParam String body , @PathVariable Long id) throws Exception{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser = applicationUserRepo.findApplicationUserByUsername(userDetails.getUsername());
        if (!id.equals(currentUser.getId())){
            throw new Exception(" you are not allowed to add a post");
        }
        Post post= new Post(body, currentUser);
        postRepo.save(post);
        return new RedirectView("/profile");
}

}
