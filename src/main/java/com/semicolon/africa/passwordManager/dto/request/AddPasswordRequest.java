package com.semicolon.africa.passwordManager.dto.request;


import lombok.Data;

@Data
public class AddPasswordRequest {
    private String name;
    private String userName;
    private String url;
    private String password;
}
