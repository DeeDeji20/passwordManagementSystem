package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.model.User;
import com.semicolon.africa.passwordManager.data.repository.PasswordManagerRepository;
import com.semicolon.africa.passwordManager.dto.request.AddPasswordRequest;
import com.semicolon.africa.passwordManager.dto.request.CreateUserRequest;
import com.semicolon.africa.passwordManager.dto.request.RetrievePasswordRequest;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;
import com.semicolon.africa.passwordManager.dto.response.RetrievePasswordResponse;
import com.semicolon.africa.passwordManager.exception.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PasswordManagerServiceTest {
    @Autowired
    PasswordManager service;

    @Autowired
    PasswordManagerRepository repository;

    @Test
    @Order(1)
    void testThatAUserCanBeCreated(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("deolaoladeji@gmail.com");
        userRequest.setPassword("ade!ola@8b9-0V");
        CreateUserResponse response = service.createUser(userRequest);
        log.info("response after save -->{}", response.getMessage());
        log.info("number of users-->{}", service.getAllUsers().size());
        assertThat(service.getAllUsers().size() , is(1));
    }


    @Test
    @Order(2)
    void testThatIfPasswordBeLessThan12Characters_throwsException(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("deolaoladeji@gmail.com");
        userRequest.setPassword("adeola");
        log.info("in 12 char-->");
        assertThrows(InvalidPasswordException.class ,()-> service.createUser(userRequest));
    }

    @Test
    @Order(3)
    void testThatPasswordCanBeAddedToTheListOfPasswords(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("dolaoladeji@gmail.com");
        userRequest.setPassword("ade!ola@8b9-0V");
        service.createUser(userRequest);

        AddPasswordRequest request = new AddPasswordRequest();
        request.setPassword("1234567");
        request.setName("dee");
        request.setUrl("www.herokuapp.org");
        request.setUserName("Deesnow200");

        service.addPassword(userRequest.getEmail(), request);

        assertThat(service.getListOfUserPassword(userRequest.getEmail()).size(),is(1));

    }

    @Test
    @Order(4)
    void testThatAnotherPasswordCanBeAddedToTheListOfPasswords(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("doleoladeji@gmail.com");
        userRequest.setPassword("ade!ola@8b9-0V");
        service.createUser(userRequest);

        AddPasswordRequest request = new AddPasswordRequest();
        request.setPassword("1234567");
        request.setName("dee");
        request.setUrl("www.herokuapp.org");
        request.setUserName("mysnow200");
        service.addPassword(userRequest.getEmail(), request);

        AddPasswordRequest request2 = new AddPasswordRequest();
        request2.setPassword("mySecondUrl");
        request2.setName("deeDeji200");
        request2.setUrl("www.google.org");
        request2.setUserName("DeeDeji");

        service.addPassword(userRequest.getEmail(), request2);

        assertThat(service.getListOfUserPassword(userRequest.getEmail()).size(),is(2));
    }

    @Test
    void testThatUserNameAndPasswordCanBeRetrievedWhenUrlIsSearchedFor(){
        RetrievePasswordRequest request = new RetrievePasswordRequest();
        request.setEmail("doleoladeji@gmail.com");
        request.setUrl("www.heroku.org");
//        service.retrieve(request);
        RetrievePasswordResponse response = service.retrieve(request);
        assertThat(response.getUserName(), is("mysnow200"));
        assertThat(response.getPassword(), is("1234567"));
    }
}