package com.Authentication.Controller;

import com.Authentication.Model.MyAppUser;
import com.Authentication.Model.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    public MyAppUserRepository myAppUserRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public MyAppUser createUser(@RequestBody MyAppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypts the password before saving
        return myAppUserRepository.save(user); // Saves the user and returns the saved entity
    }
}
