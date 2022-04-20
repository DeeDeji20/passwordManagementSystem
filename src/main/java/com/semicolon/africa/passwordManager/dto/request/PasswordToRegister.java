package com.semicolon.africa.passwordManager.dto.request;

import lombok.Data;

@Data
public class PasswordToRegister {
    private int id ;
    private String name;
    private String userName;
    private String url;
    private String password;

    public void setId(int id) {
        this.id = id+1;
    }
}
