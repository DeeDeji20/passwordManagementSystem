package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.model.User;
import com.semicolon.africa.passwordManager.dto.request.AddPasswordRequest;
import com.semicolon.africa.passwordManager.dto.request.CreateUserRequest;
import com.semicolon.africa.passwordManager.dto.request.PasswordToRegister;
import com.semicolon.africa.passwordManager.dto.response.AddPasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;

import java.util.Collection;
import java.util.List;
import java.util.stream.DoubleStream;

public interface PasswordManager {
    CreateUserResponse createUser(CreateUserRequest request);
    AddPasswordResponse addPassword(String userName, AddPasswordRequest request);

    List<User> getAllUsers();

    List<PasswordToRegister> getListOfUserPassword();
}
