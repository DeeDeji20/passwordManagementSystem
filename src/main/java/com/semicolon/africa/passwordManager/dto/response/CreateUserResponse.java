package com.semicolon.africa.passwordManager.dto.response;

import lombok.Data;

@Data
public class CreateUserResponse {
    private String message;
    private String email;
    private String password;
}
