package com.Authentication.Controller;

import com.Authentication.Model.MyAppUser;
import com.Authentication.Model.MyAppUserRepository;
import com.Authentication.Service.EmailService;
import com.Authentication.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public ResponseEntity<String> createUser(@RequestBody MyAppUser user) {

        MyAppUser existingAppUser = myAppUserRepository.findByEmail(user.getEmail()); // Checks if a user with the same email already exists
        if (existingAppUser != null) {
            if (existingAppUser.isVerified()){
                return new ResponseEntity<>("User already exists with this email and verified",
                        HttpStatus.BAD_REQUEST);
            }else {
                String verificationToken = JwtTokenUtil.generateToken(user.getEmail());
                existingAppUser.setVerificationToken(verificationToken); // Sets a new verification token for the existing user
                myAppUserRepository.save(existingAppUser);
                emailService.sendVerificationEmail(existingAppUser.getEmail(), verificationToken); // Sends a verification email to the existing user
                return new ResponseEntity<>("Check your email for verification link",
                        HttpStatus.OK);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypts the password before saving
        String verificationToken = JwtTokenUtil.generateToken(user.getEmail());
        user.setVerificationToken(verificationToken); // Sets a verification token for the new user
        myAppUserRepository.save(user); // Saves the user and returns the saved entity
        emailService.sendVerificationEmail(user.getEmail(), verificationToken); // Sends a verification email to the new user
        return new ResponseEntity<>("Registration successful, check your email for verification link", HttpStatus.OK);
    }
}
