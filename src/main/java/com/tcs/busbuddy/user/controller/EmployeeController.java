package com.tcs.busbuddy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.tcs.busbuddy.user.model.AppUser;
import com.tcs.busbuddy.user.repository.AppUserRepository;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @GetMapping("/dashboard")
    public String userDashboard() {
        return "Welcome, Employee! You have employee-level access.";
    }
}
