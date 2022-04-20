package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.model.User;
import com.semicolon.africa.passwordManager.dto.request.*;
import com.semicolon.africa.passwordManager.dto.response.AddPasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;
import com.semicolon.africa.passwordManager.dto.response.RetrievePasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.UserLoginResponse;

import java.util.List;

public interface PasswordManager {
    CreateUserResponse createUser(CreateUserRequest request);
    AddPasswordResponse addPassword(AddPasswordRequest request);

    List<User> getAllUsers();

    List<PasswordToRegister> getListOfUserPassword(String email);

    RetrievePasswordResponse retrieve(RetrievePasswordRequest request);

    UserLoginResponse loginUser(UserLoginRequest userLogin);

    void delete(int passwordId, String email);
}
