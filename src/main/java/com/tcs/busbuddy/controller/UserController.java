package com.tcs.busbuddy.controller;

import com.tcs.busbuddy.model.AppUser;
import com.tcs.busbuddy.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class UserController {

    

    

    @GetMapping("/dashboard")
    public String userDashboard() {
        return "Welcome, Employee! You have employee-level access.";
    }
}
