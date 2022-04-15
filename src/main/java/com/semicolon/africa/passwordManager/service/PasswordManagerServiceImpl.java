package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.model.User;
import com.semicolon.africa.passwordManager.data.repository.PasswordManagerRepository;
import com.semicolon.africa.passwordManager.dto.request.AddPasswordRequest;
import com.semicolon.africa.passwordManager.dto.request.CreateUserRequest;
import com.semicolon.africa.passwordManager.dto.request.PasswordToRegister;
import com.semicolon.africa.passwordManager.dto.response.AddPasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;
import com.semicolon.africa.passwordManager.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PasswordManagerServiceImpl implements PasswordManager{
//    @Autowired
    PasswordManager manager;
    @Autowired
    PasswordManagerRepository database;

//    private User user = new User();

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        if (validatePassword(request.getPassword()))throw new InvalidPasswordException("Invalid passsword");
        User user = new User();
        user.setEmail(request.getEmail());
        user.setRegistrationPassword(request.getPassword());
        database.save(user);
        CreateUserResponse response = new CreateUserResponse();
        response.setMessage("User created");
        return response;
    }

    private boolean  validatePassword(String password) {
        return ((password.length() < 12
                && !password.matches("[?./,<>!@#$%^&*_+-=|]")
                && !password.matches("[A-Z]")
                && !password.matches("[0-9]")
                && !password.matches("[a-z]")));
    }

    @Override
    public AddPasswordResponse addPassword(String email, AddPasswordRequest request) {
        User user = database.findByEmail(email);
        PasswordToRegister passwordToRegister = new PasswordToRegister();
        passwordToRegister.setPassword(request.getPassword());
        passwordToRegister.setUrl(request.getUrl());
        passwordToRegister.setUserName(request.getUserName());
        passwordToRegister.setName(request.getName());
        user.getRegisteredPassword().add(passwordToRegister);

        database.save(user);
        System.out.println(user);
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return database.findAll();
    }

    @Override
    public List<PasswordToRegister> getListOfUserPassword() {
        return null;
    }
}
