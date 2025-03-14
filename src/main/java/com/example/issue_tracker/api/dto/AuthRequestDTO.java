package com.example.issue_tracker.api.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String password;
}
