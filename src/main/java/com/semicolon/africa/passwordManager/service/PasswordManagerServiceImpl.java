package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.model.User;
import com.semicolon.africa.passwordManager.data.repository.PasswordManagerRepository;
import com.semicolon.africa.passwordManager.dto.request.*;
import com.semicolon.africa.passwordManager.dto.response.AddPasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;
import com.semicolon.africa.passwordManager.dto.response.RetrievePasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.UserLoginResponse;
import com.semicolon.africa.passwordManager.exception.InvalidPasswordException;
import com.semicolon.africa.passwordManager.exception.InvalidUserException;
import com.semicolon.africa.passwordManager.exception.NonExistentUrlexception;
import com.semicolon.africa.passwordManager.exception.UserNotLoggedInException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@Slf4j
public class PasswordManagerServiceImpl implements PasswordManager{

    @Autowired
    PasswordManagerRepository database;


    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = new User();
        if (validatePassword(request.getPassword()))throw new InvalidPasswordException("Invalid passsword");
        user.setEmail(request.getEmail());
        user.setRegistrationPassword(request.getPassword());
        log.info("no. of users before save-->{}", database.findAll().size());
        database.save(user);
        log.info("no. of users after save-->{}", database.findAll().size());

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
    public AddPasswordResponse addPassword(AddPasswordRequest request) {
        User user = database.findByEmail(request.getEmail());
        if (database.findByEmail(request.getEmail()) == null) throw new InvalidUserException("Not a valid user");
        System.out.println(user.isLoginStatus()+ "===>");
        if (!user.isLoginStatus()) throw new UserNotLoggedInException("User not logged in");
        PasswordToRegister passwordToRegister = new PasswordToRegister();
        passwordToRegister.setId(getListOfUserPassword(request.getEmail()).size()+1);
        passwordToRegister.setName(request.getName());
        passwordToRegister.setPassword(request.getPassword());
        passwordToRegister.setUrl(request.getUrl());
        passwordToRegister.setUserName(request.getUserName());

        user.getRegisteredPassword().add(passwordToRegister);
        database.save(user);

        AddPasswordResponse response = new AddPasswordResponse();
        response.setMessage("password to " + request.getUrl() + " has been added");
        return response;
    }

    @Override
    public List<User> getAllUsers() {
        return database.findAll();
    }

    @Override
    public List<PasswordToRegister> getListOfUserPassword(String email) {
        User user = database.findByEmail(email);
        log.info(String.valueOf(user.getRegisteredPassword().size()));
        return user.getRegisteredPassword();
    }

    @Override
    public RetrievePasswordResponse retrieve(RetrievePasswordRequest request) {
        User user = database.findByEmail(request.getEmail());
        log.info(user.getEmail());
        List<PasswordToRegister> passwords = getListOfUserPassword(request.getEmail());
        RetrievePasswordResponse response = new RetrievePasswordResponse();
        passwords.forEach(password ->{
            if(password.getUrl().equals(request.getUrl())){
                response.setPassword(password.getPassword());
                response.setUserName(password.getUserName());
            }
            else throw new NonExistentUrlexception("Non-existent url password");
        });
        return response;
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLogin) {
        User user = database.findByEmail(userLogin.getEmail());
        UserLoginResponse response = new UserLoginResponse();
        if(userLogin.getPassword().equals(user.getRegistrationPassword())){
            user.setLoginStatus(true);
            response.setMessage(user.getEmail() + " has been logged in");
            System.out.println(user.isLoginStatus());
            database.save(user);
        }
        return response;
    }

    @Override
    public void delete(int passwordId, String email) {
        User user = database.findByEmail(email);
        List<PasswordToRegister> passwords = user.getRegisteredPassword();
        if(passwords.contains(passwords.get(passwordId))){
            passwords.remove(passwords.get(passwordId));
            database.save(user);
        }

    }

    @Override
    public void update(int passwordId, UpdatePasswordRequest updateRequest) {

    }
}
