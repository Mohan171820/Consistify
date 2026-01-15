package com.example.Streaker.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//This generates the Bcrypt code for the password of the user
public class BCryptGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("test123"));
    }
}
