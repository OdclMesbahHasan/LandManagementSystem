package com.odcl.lms.auth.service;

import com.odcl.lms.auth.dto.UserLoginDto;
import com.odcl.lms.auth.dto.UserRegistrationDto;
import com.odcl.lms.auth.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto userRegistrationDto);
    User login(UserLoginDto loginDto);
    public UserDetails loadUserByUsername(String email);
}
