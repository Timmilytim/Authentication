package com.Authentication.Model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyAppUserRepository extends JpaRepository<MyAppUser, Long> {

    Optional<MyAppUser> findByUsername(String username); // Method to find a user by username
    MyAppUser findByEmail(String email); // Method to find a user by email
}
