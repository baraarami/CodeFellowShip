package com.example.CodeFellowship.web;


import com.example.CodeFellowship.domain.ApplicationUser;
import com.example.CodeFellowship.domain.Post;
import com.example.CodeFellowship.interfaces.ApplicationUserRepo;
import com.example.CodeFellowship.interfaces.PostRepo;
import com.example.CodeFellowship.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder encoder;


    @GetMapping("/myprofile")
    public String getProfilepage(ModelMap model , HttpServletRequest request){
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ApplicationUser applicationUser = userService.findApplicationUserByUsername(userDetails.getUsername());

        return profileData(model , applicationUser , true , applicationUser.getId() , true);
    }

    private String profileData (ModelMap model , ApplicationUser applicationUser
     , boolean isAuthorized , Long id , boolean showPostForm){
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
        model.addAttribute("id" ,id);


        return "profile";

    }


    @GetMapping("users")
    public String getAllUsers(Model model){
        List<ApplicationUser> users = userService.findAll();
        model.addAttribute("users " , users);

        return "users.html";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable Long id , ModelMap model){
        ApplicationUser applicationUser= userService.findById(id);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser currentUser = userService.findApplicationUserByUsername(userDetails.getUsername());

//        boolean showPostForm;
//        showPostForm= applicationUser.getId().equals(currentUser.getId());
//
            Long passedId = applicationUser.getId();
            boolean isAuthorized = applicationUser.getId().equals(currentUser.getId());
            for (GrantedAuthority role : userDetails.getAuthorities()){
                if (role.toString().equals("ADMIN" || applicationUser.getId().equals(currentUser.getId()))){
                    isAuthorized = true;
                    passedId=id;
                }
            }

        return profileData(model , applicationUser , isAuthorized, passedId, false);


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
