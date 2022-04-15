package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.model.User;
import com.semicolon.africa.passwordManager.data.repository.PasswordManagerRepository;
import com.semicolon.africa.passwordManager.dto.request.AddPasswordRequest;
import com.semicolon.africa.passwordManager.dto.request.CreateUserRequest;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;
import com.semicolon.africa.passwordManager.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PasswordManagerServiceTest {
    @Autowired
    PasswordManager service;

    @Autowired
    PasswordManagerRepository repository;

    @Test
    void testThatAUserCanBeCreated(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("deolaoladeji@gmail.com");
        userRequest.setPassword("ade!ola@8b9-0V");

        service.createUser(userRequest);
        assertThat(service.getAllUsers().size() , is(1));

    }

    @Test
    void testThatIfPasswordBeLessThan12Characters_throwsException(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("deolaoladeji@gmail.com");
        userRequest.setPassword("adeola");
        assertThrows(InvalidPasswordException.class ,()-> service.createUser(userRequest));
    }

    @Test
    void testThatPasswordCanBeAddedToTheListOfPasswords(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("deolaoladeji@gmail.com");
        userRequest.setPassword("ade!ola@8b9-0V");
        service.createUser(userRequest);


        AddPasswordRequest request = new AddPasswordRequest();
        request.setPassword("1234567");
        request.setName("dee");
        request.setUrl("www.herokuapp.org");
        request.setUserName("Deesnow200");

        service.addPassword(userRequest.getEmail(), request);

        assertThat(service.getListOfUserPassword().size(), is(1));

    }
}