package com.semicolon.africa.passwordManager.data.model;

import com.semicolon.africa.passwordManager.dto.request.PasswordToRegister;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("User")
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true)
    private String email;
    @NonNull
    private String registrationPassword;
    private boolean loginStatus;
    private List<PasswordToRegister> registeredPassword = new ArrayList<>();
}
