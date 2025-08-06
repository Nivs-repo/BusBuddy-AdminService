package com.tcs.busbuddy.user.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.tcs.busbuddy.common.util.ResponseBuilder;
import com.tcs.busbuddy.common.dto.ApiResponse;
import com.tcs.busbuddy.common.exception.BusinessException;
import com.tcs.busbuddy.user.dto.LoginRequest;
import com.tcs.busbuddy.user.dto.LoginResponse;
import com.tcs.busbuddy.user.model.AppUser;
import com.tcs.busbuddy.user.repository.AppUserRepository;
import com.tcs.busbuddy.user.security.CustomUserDetails;
import com.tcs.busbuddy.user.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Register User
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody AppUser user) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BusinessException("USERNAME_ALREADY_EXISTS", "This username is already registered.");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseBuilder.buildSuccessResponse("User registered successfully!");
    }

    // ✅ Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Step 1: Authenticate credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));

            // Step 2: Generate JWT using authenticated user
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            // Step 3: Return token in centralized response
            return ResponseBuilder.buildSuccessResponse(new LoginResponse(token));

        } catch (Exception ex) {
            throw new BusinessException("INVALID_CREDENTIALS", "Username or password is incorrect.");
        }
    }
}
