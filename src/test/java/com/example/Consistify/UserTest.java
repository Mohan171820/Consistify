package com.example.Consistify;

import com.example.Consistify.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    public void testUser() {
        User user = User.builder()
                .email("test@gmail.com")
                .build();
        assertThat(user.getUsername()).isEqualTo("test@gmail.com");
        System.out.println("tested USer");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Before Test");
    }
    @Test
    void TestPassword() {
        User user = User.builder()
                .password("test@123")
                .build();
        assertThat(user.getPassword()).isEqualTo("test@123");
    }
}
