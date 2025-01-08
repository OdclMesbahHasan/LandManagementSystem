package com.odcl.lms.auth.service;

import com.odcl.lms.auth.dto.UserLoginDto;
import com.odcl.lms.auth.dto.UserRegistrationDto;
import com.odcl.lms.auth.model.User;
import com.odcl.lms.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User save(UserRegistrationDto userRegistrationDto) {
        String encodedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());

        User user = new User(
                userRegistrationDto.getId(),
                userRegistrationDto.getFirstName(),
                userRegistrationDto.getLastName(),
                userRegistrationDto.getEmail(),
                encodedPassword,
                userRegistrationDto.getUserId()

        );

        validateUserRegistrationDto(userRegistrationDto);

        return userRepository.save(user);
    }

    public User login(UserLoginDto loginDto) {
        // Determine if input is an email or userId
        Optional<User> optionalUser;
        if (isEmail(loginDto.getEmailOrUserId())) {
            // Query by email
            optionalUser = userRepository.findByEmail(loginDto.getEmailOrUserId());
        } else {
            // Query by userId
            optionalUser = userRepository.findByUserId(loginDto.getEmailOrUserId());
        }

        if (optionalUser.isEmpty()) {
            Throwable cause = new IllegalArgumentException("Invalid email/user ID or password.");
            throw new RuntimeException(cause);
        }

        User user = optionalUser.get();

        // Verify the password
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            Throwable cause = new IllegalArgumentException("Invalid password.");
            throw new RuntimeException(cause);
        }

        return user;
    }

    private boolean isEmail(String input) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return input.matches(emailRegex);
    }


    private void validateUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
        // Validate each field using switch case
        String[] fieldNames = {
                "firstName", "lastName", "email", "password", "userId"
        };

        for (String fieldName : fieldNames) {
            switch (fieldName) {
                case "firstName":
                    if (checkField(userRegistrationDto.getFirstName())) {
                        Throwable cause = new IllegalArgumentException("First Name cannot be null or empty");
                        throw new RuntimeException(cause);
                    }
                    break;
                case "lastName":
                    if (checkField(userRegistrationDto.getLastName())) {
                        Throwable cause = new IllegalArgumentException("Last Name cannot be null or empty");
                        throw new RuntimeException(cause);
                    }
                    break;
                case "email":
                    if (checkField(userRegistrationDto.getEmail())) {
                        Throwable cause = new IllegalArgumentException("Email cannot be null or empty");
                        throw new RuntimeException(cause);
                    }
                    Optional<User> existingUserByEmail = userRepository.findByEmail(userRegistrationDto.getEmail());
                    if (existingUserByEmail.isPresent()) {
                        Throwable cause = new IllegalArgumentException("Email already exists");
                        throw new RuntimeException(cause);
                    }

                    break;
                case "password":
                    if (checkField(userRegistrationDto.getPassword())) {
                        Throwable cause = new IllegalArgumentException("password cannot be null or empty");
                        throw new RuntimeException(cause);
                    }
                    break;
                case "userId":
                    if (checkField(userRegistrationDto.getUserId())) {
                        Throwable cause = new IllegalArgumentException("User Id cannot be null or empty");
                        throw new RuntimeException(cause);
                    }
                    // Check if the userId already exists in the database (if necessary)
                    Optional<User> existingUserByUserId = userRepository.findByUserId(userRegistrationDto.getUserId());
                    if (existingUserByUserId.isPresent()) {
                        Throwable cause = new IllegalArgumentException("User ID is already in use.");
                        throw new RuntimeException(cause);
                    }
                    break;
                default:
                    Throwable cause = new IllegalArgumentException("Unexpected field: " + fieldName);
                    throw new RuntimeException(cause);
            }
        }
    }

    private boolean checkField(String field) {
        return field == null || field.trim().isEmpty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

