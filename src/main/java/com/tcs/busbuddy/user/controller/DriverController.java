package com.tcs.busbuddy.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @GetMapping("/dashboard")
    public String driverDashboard() {
        return "Welcome, DRIVER! You have driver-level access.";
    }
}
