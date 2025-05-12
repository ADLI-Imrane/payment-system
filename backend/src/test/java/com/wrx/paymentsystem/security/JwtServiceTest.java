package com.wrx.paymentsystem.security;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() throws Exception {
        jwtService = new JwtService();
        userDetails = User.builder()
                .username("testUser")
                .password("password")
                .roles("USER")
                .build();

        // Set private fields using reflection
        setPrivateField(jwtService, "secretKey", "test-secret-key-1234567890-1234567890-1234567890");
        setPrivateField(jwtService, "jwtExpiration", 86400000L); // 24 hours
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testGenerateAndValidateToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken(userDetails);
        assertEquals(userDetails.getUsername(), jwtService.extractUsername(token));
    }

    @Test
    public void testTokenValidityWithDifferentUser() {
        String token = jwtService.generateToken(userDetails);
        
        UserDetails otherUser = User.builder()
                .username("otherUser")
                .password("password")
                .roles("USER")
                .build();
        
        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    public void testTokenExpiration() throws Exception {
        // Set very short expiration (1ms)
        setPrivateField(jwtService, "jwtExpiration", 1L);
        
        String token = jwtService.generateToken(userDetails);
        Thread.sleep(2); // Wait longer than expiration
        
        assertFalse(jwtService.isTokenValid(token, userDetails));
    }
}