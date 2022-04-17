package com.semicolon.africa.passwordManager.data.repository;

import com.semicolon.africa.passwordManager.data.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PasswordManagerRepositoryTest {
@Autowired
PasswordManagerRepository passwordManagerRepository;
    @Test
    void findByEmail() {
        User user = passwordManagerRepository.save(new User("john@email.com", "1234"));
        assertThat(user).isNotNull();
        assertThat(passwordManagerRepository.findByEmail("john@email.com")).isNotNull();
        User foundUser = passwordManagerRepository.findByEmail("john@email.com");
        log.info("user is-->{}", foundUser.getEmail());
    }

    @Test
    void findAllUsers(){
        passwordManagerRepository.save(new User("john@email.com", "1234"));
        List<User> allUsers= passwordManagerRepository.findAll();
        log.info("total number of users is --->{}", allUsers.size());
    }


    @Test
    void deleteUserByEmail() {
    }
}