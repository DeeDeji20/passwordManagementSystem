package com.semicolon.africa.passwordManager.controllers;

import com.semicolon.africa.passwordManager.dto.request.CreateUserRequest;
import com.semicolon.africa.passwordManager.dto.response.ApiResponse;
import com.semicolon.africa.passwordManager.dto.response.CreateUserResponse;
import com.semicolon.africa.passwordManager.exception.InvalidUserException;
import com.semicolon.africa.passwordManager.service.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PasswordManagerController {
    @Autowired
    PasswordManager passwordManager;


    @GetMapping
    public String home(){
        return "home";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute CreateUserRequest request, Model model){
        CreateUserResponse response = passwordManager.createUser(request);
        model.addAttribute("response", response);
        return "result";
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request){
        try {
            return new ResponseEntity<>(passwordManager.createUser(request), HttpStatus.OK);
        }catch(InvalidUserException e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
