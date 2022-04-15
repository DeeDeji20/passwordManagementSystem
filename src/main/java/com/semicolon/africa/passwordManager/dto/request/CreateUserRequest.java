package com.semicolon.africa.passwordManager.dto.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
}
