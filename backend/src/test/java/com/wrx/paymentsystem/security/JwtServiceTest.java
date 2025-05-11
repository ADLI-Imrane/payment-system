package com.wrx.paymentsystem.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
    }

    @Test
    public void testGenerateToken() {
        String username = "testUser";
        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")); // Check the header part of the JWT
    }

    @Test
    public void testExtractUsername() {
        String username = "testUser";
        String token = jwtService.generateToken(username);
        
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testIsTokenExpired() {
        String username = "testUser";
        String token = jwtService.generateToken(username);
        
        assertFalse(jwtService.isTokenExpired(token)); // Token should not be expired immediately

        // Simulate expiration by modifying expiration date or mock it in real scenarios.
        // You could mock Date.now() to test this behavior
    }

    @Test
    public void testValidateToken() {
        String username = "testUser";
        String token = jwtService.generateToken(username);
        
        boolean isValid = jwtService.validateToken(token, username);
        assertTrue(isValid);
    }
}
