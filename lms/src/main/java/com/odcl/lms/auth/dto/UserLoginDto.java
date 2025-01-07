package com.odcl.lms.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDto {
    @NotBlank(message = "Email or User ID is required.")
    private String emailOrUserId;

    @NotBlank(message = "Password is required.")
    private String password;

    // Getters and Setters
    public String getEmailOrUserId() {
        return emailOrUserId;
    }

    public void setEmailOrUserId(String emailOrUserId) {
        this.emailOrUserId = emailOrUserId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
