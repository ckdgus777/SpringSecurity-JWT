package com.example.springsecurityjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/superadmin")
    public String superadmin(){
        return "superadmin";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }

}

