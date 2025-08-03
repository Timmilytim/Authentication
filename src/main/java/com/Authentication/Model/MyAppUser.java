package com.Authentication.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MyAppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Unique identifier for the user
    private String username; // Username for the user
    @Column(unique = true, nullable = false)
    private String email; // Email address of the user
    private String password; // Password for the user
    private String verificationToken; // Token for email verification
    private boolean isVerified; // Flag to check if the user is verified

}
