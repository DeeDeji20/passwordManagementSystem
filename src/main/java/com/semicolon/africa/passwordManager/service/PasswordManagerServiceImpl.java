package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.model.User;
import com.semicolon.africa.passwordManager.data.repository.PasswordManagerRepository;
import com.semicolon.africa.passwordManager.dto.request.AddPasswordRequest;
import com.semicolon.africa.passwordManager.dto.request.CreateUserRequest;
import com.semicolon.africa.passwordManager.dto.request.PasswordToRegister;
import com.semicolon.africa.passwordManager.dto.request.RetrievePasswordRequest;
import com.semicolon.africa.passwordManager.dto.response.AddPasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;
import com.semicolon.africa.passwordManager.dto.response.RetrievePasswordResponse;
import com.semicolon.africa.passwordManager.exception.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class PasswordManagerServiceImpl implements PasswordManager{

    @Autowired
    PasswordManagerRepository database;

//    @Autowired
//   User user = new User();

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
    public AddPasswordResponse addPassword(String email, AddPasswordRequest request) {
        PasswordToRegister passwordToRegister = new PasswordToRegister();
        passwordToRegister.setName(request.getName());
        passwordToRegister.setPassword(request.getPassword());
        passwordToRegister.setUrl(request.getUrl());
        passwordToRegister.setUserName(request.getUserName());

        User user = database.findByEmail(email);
        user.getRegisteredPassword().add(passwordToRegister);
        log.info("users in db-->{}", database.findAll());
        database.save(user);
        log.info("users in db after-->{}", database.findAll());


        log.info(String.valueOf(user.getRegisteredPassword().size()));
        return null;
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
        });
        return response;
    }
}




//
//
//    User user = new User();
////        ListIterator<User> iterator = getAllUsers().listIterator();
//        user = database.findByEmail(email);
//                List<User> users = database.findAll();
//
////        System.out.println(user);
//        PasswordToRegister passwordToRegister = new PasswordToRegister();
//        passwordToRegister.setPassword(request.getPassword());
//        passwordToRegister.setUrl(request.getUrl());
//        passwordToRegister.setUserName(request.getUserName());
//        passwordToRegister.setName(request.getName());
//        users.forEach(userx ->{
//        if(Objects.equals(userx.getEmail(), email)){
//        log.info(" ............");
//        database.deleteUserByEmail(userx.getEmail());
//        }
//        });
//        user.getRegisteredPassword().add(passwordToRegister);
//        log.info(String.valueOf(user));
//        database.save(user);
//
////        System.out.println(user);
//        log.info(String.valueOf(database.findAll()));
////        ListIterator <User> listIterator = database.findAll().listIterator();
////        while(listIterator.hasNext()) System.out.println(listIterator.next());
