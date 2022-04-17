package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.model.User;
import com.semicolon.africa.passwordManager.dto.request.AddPasswordRequest;
import com.semicolon.africa.passwordManager.dto.request.CreateUserRequest;
import com.semicolon.africa.passwordManager.dto.request.PasswordToRegister;
import com.semicolon.africa.passwordManager.dto.request.RetrievePasswordRequest;
import com.semicolon.africa.passwordManager.dto.response.AddPasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;
import com.semicolon.africa.passwordManager.dto.response.RetrievePasswordResponse;

import java.util.List;

public interface PasswordManager {
    CreateUserResponse createUser(CreateUserRequest request);
    AddPasswordResponse addPassword(String userName, AddPasswordRequest request);

    List<User> getAllUsers();

    List<PasswordToRegister> getListOfUserPassword(String email);

    RetrievePasswordResponse retrieve(RetrievePasswordRequest request);
}
