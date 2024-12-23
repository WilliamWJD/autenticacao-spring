package com.auth.example.dto;

import com.auth.example.enums.UserRole;

public record RegisterDTO(
        String login,
        String password,
        UserRole role
) {
}
