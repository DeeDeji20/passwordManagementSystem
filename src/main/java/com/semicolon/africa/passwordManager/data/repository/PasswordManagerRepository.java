package com.semicolon.africa.passwordManager.data.repository;

import com.semicolon.africa.passwordManager.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordManagerRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    void deleteUserByEmail(String email);
}
