package com.odcl.lms.auth.controller;

import com.odcl.lms.auth.dto.UserLoginDto;
import com.odcl.lms.auth.dto.UserRegistrationDto;
import com.odcl.lms.auth.model.User;
import com.odcl.lms.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        // Save the user
        userService.save(userRegistrationDto);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto loginDto) {
        User user = userService.login(loginDto);
        return ResponseEntity.ok("Login successful for user: " + user.getFirstName() + " " + user.getLastName());
    }

}
