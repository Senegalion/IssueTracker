package com.example.issue_tracker.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
}
