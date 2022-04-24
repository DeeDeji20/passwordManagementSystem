package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.model.User;
import com.semicolon.africa.passwordManager.dto.request.*;
import com.semicolon.africa.passwordManager.dto.response.*;

import java.util.List;

public interface PasswordManager {
    CreateUserResponse createUser(CreateUserRequest request);
    AddPasswordResponse addPassword(AddPasswordRequest request);

    List<User> getAllUsers();

    List<PasswordToRegister> getListOfUserPassword(String email);

    RetrievePasswordResponse retrieve(RetrievePasswordRequest request, String s);

    UserLoginResponse loginUser(UserLoginRequest userLogin);

    void delete(int passwordId, String email);

    UpdateResponse update(int passwordId, UpdatePasswordRequest updateRequest);
}
