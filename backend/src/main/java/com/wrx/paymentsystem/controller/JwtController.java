package com.wrx.paymentsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wrx.paymentsystem.security.JwtService;

@RestController
@RequestMapping("/api")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/generate-token")
    public String generateToken(@RequestParam String username) {
        // Create a simple UserDetails object for token generation
        UserDetails userDetails = User.withUsername(username)
            .password("") // Password not needed for token generation
            .authorities("ROLE_USER") // Default role
            .build();
        return jwtService.generateToken(userDetails);
    }

    @GetMapping("/validate-token")
    public boolean validateToken(@RequestParam String token, @RequestParam String username) {
        // Create a simple UserDetails for validation
        UserDetails userDetails = User.withUsername(username)
            .password("") // Password not needed for validation
            .authorities("ROLE_USER") // Default role
            .build();
        return jwtService.isTokenValid(token, userDetails);
    }
}