package com.odcl.lms.auth.controller;

import com.odcl.lms.auth.dto.UserRegistrationDto;
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

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            // Authenticate the user using the provided email and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRegistrationDto.getEmail(), userRegistrationDto.getPassword()
                    )
            );

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Respond with a success message
            return ResponseEntity.ok("Login successful");
        } catch (Exception e) {
            // Handle authentication failure
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
