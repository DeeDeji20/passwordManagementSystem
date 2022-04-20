package com.semicolon.africa.passwordManager.service;

import com.semicolon.africa.passwordManager.data.repository.PasswordManagerRepository;
import com.semicolon.africa.passwordManager.dto.request.AddPasswordRequest;
import com.semicolon.africa.passwordManager.dto.request.CreateUserRequest;
import com.semicolon.africa.passwordManager.dto.request.RetrievePasswordRequest;
import com.semicolon.africa.passwordManager.dto.request.UserLoginRequest;
import com.semicolon.africa.passwordManager.dto.response.AddPasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;
import com.semicolon.africa.passwordManager.dto.response.RetrievePasswordResponse;
import com.semicolon.africa.passwordManager.dto.response.UserLoginResponse;
import com.semicolon.africa.passwordManager.exception.InvalidPasswordException;
import com.semicolon.africa.passwordManager.exception.InvalidUserException;
import com.semicolon.africa.passwordManager.exception.NonExistentUrlexception;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
    void testThatRegisteredUserCanLogin(){
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("increase@gmail.com");
        request.setPassword("incBabe@5000");
        service.createUser(request);
        assertThat(service.getAllUsers().size() , is(1));

        UserLoginRequest userLogin = new UserLoginRequest("increase@gmail.com", "incBabe@5000");
         UserLoginResponse response= service.loginUser(userLogin);
         assertThat(response.getMessage(), is(request.getEmail()+" has been logged in"));
    }

    @Test
    void testThatInvalidUsersCannotAddPasswords_throwsException(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("dolaoladeji@gmail.com");
        userRequest.setPassword("ade!ola@8b9-0V");
        service.createUser(userRequest);

        AddPasswordRequest request = new AddPasswordRequest();
        request.setPassword("1234567");
        request.setName("dee");
        request.setUrl("www.herokuapp.org");
        request.setUserName("Deesnow200");
        request.setEmail("unknownEmail@gmail.com");

       assertThrows(InvalidUserException.class, ()-> service.addPassword( request));

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
        request.setEmail(userRequest.getEmail());

        service.addPassword(request);

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
        request.setEmail("doleoladeji@gmail.com");
        service.addPassword( request);

        AddPasswordRequest request2 = new AddPasswordRequest();
        request2.setPassword("mySecondUrl");
        request2.setName("deeDeji200");
        request2.setUrl("www.google.org");
        request2.setUserName("DeeDeji");
        request2.setEmail("doleoladeji@gmail.com");

        service.addPassword(request2);

        assertThat(service.getListOfUserPassword(userRequest.getEmail()).size(),is(2));
    }

    @Test
    void testThatAddPasswordResponseCanBeGotten(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("doleoladeji@gmail.com");
        userRequest.setPassword("ade!ola@8b9-0V");
        service.createUser(userRequest);

        AddPasswordRequest request = new AddPasswordRequest();
        request.setPassword("1234567");
        request.setName("dee");
        request.setUrl("www.herokuapp.org");
        request.setUserName("mysnow200");
        request.setEmail(userRequest.getEmail());
        AddPasswordResponse response = service.addPassword(request);
        assertThat(response.getMessage(), is("password to " + request.getUrl() + " has been added"));
    }

    @Test
    @Order(5)
    void testThatUserNameAndPasswordCanBeRetrievedWhenUrlIsSearchedFor(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("lotachi@gmail.com");
        userRequest.setPassword("ade!ola@8b9-0V");
        service.createUser(userRequest);

        AddPasswordRequest addRequest = new AddPasswordRequest();
        addRequest.setPassword("myDeeDeji");
        addRequest.setName("youtube");
        addRequest.setUrl("www.youtube.org");
        addRequest.setUserName("myYoutubeUserName");
        addRequest.setEmail(userRequest.getEmail());
        service.addPassword(addRequest);

        RetrievePasswordRequest request = new RetrievePasswordRequest();
        request.setEmail("lotachi@gmail.com");
        request.setUrl("www.youtube.org");
//        service.retrieve(request);
        RetrievePasswordResponse response = service.retrieve(request);
        assertThat(response.getUserName(), is("myYoutubeUserName"));
        assertThat(response.getPassword(), is("myDeeDeji"));
    }

    @Test
    @Order(6)
    void testThatUserNameAndPasswordThatDoesntExists_throwsException(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("esther@gmail.com");
        userRequest.setPassword("esthergirl@!90A");
        service.createUser(userRequest);

        AddPasswordRequest addRequest = new AddPasswordRequest();
        addRequest.setPassword("myDeeDeji");
        addRequest.setName("customSite");
        addRequest.setUrl("www.myCustomSite.org");
        addRequest.setUserName("customSite");
        addRequest.setEmail(userRequest.getEmail());
        service.addPassword(addRequest);

        RetrievePasswordRequest request = new RetrievePasswordRequest();
        request.setEmail("esther@gmail.com");
        request.setUrl("www.nonExistentSite.org");
        assertThrows(NonExistentUrlexception.class, ()-> service.retrieve(request));
    }

    @Test
    void testThatAPasswordCanBeDeletedFromListsOfPasswords(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setEmail("mercy@gmail.com");
        userRequest.setPassword("mercygirl@!90A");
        service.createUser(userRequest);

        AddPasswordRequest addRequest = new AddPasswordRequest();
        addRequest.setPassword("myDeeDeji");
        addRequest.setName("customSite");
        addRequest.setUrl("www.myCustomSite.org");
        addRequest.setUserName("customSite");
        addRequest.setEmail(userRequest.getEmail());
        service.addPassword(addRequest);

        AddPasswordRequest addRequest2 = new AddPasswordRequest();
        addRequest2.setPassword("randomPassword");
        addRequest2.setName("dellwebsite");
        addRequest2.setUrl("www.dell.org");
        addRequest2.setUserName("cell");
        addRequest2.setEmail(userRequest.getEmail());
        service.addPassword(addRequest2);

        service.delete(1);
    }

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }
}