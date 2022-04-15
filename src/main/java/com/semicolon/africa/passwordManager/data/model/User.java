package com.semicolon.africa.passwordManager.data.model;

import com.semicolon.africa.passwordManager.dto.request.PasswordToRegister;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("User")
@Component
public class User {
    private String email;
    private String registrationPassword;
    private List<PasswordToRegister> registeredPassword = new ArrayList<>();


}
