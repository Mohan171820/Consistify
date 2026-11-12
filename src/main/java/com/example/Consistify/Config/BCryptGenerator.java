package com.example.Consistify.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// This class is used to generate a BCrypt encrypted password
public class BCryptGenerator {

    public static void main(String[] args) {

        // Create BCrypt password encoder object
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Encode the plain text password and print the encrypted value
        System.out.println(encoder.encode("test123"));
    }
}
