package com.example.Consistify.DTO;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String name;
}