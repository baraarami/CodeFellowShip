package com.example.CodeFellowship.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {

    @GetMapping("/")
    public String homePage(){
        return "index.html";
    }
}
