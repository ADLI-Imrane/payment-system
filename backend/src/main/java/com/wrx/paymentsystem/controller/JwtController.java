package com.wrx.paymentsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
        return jwtService.generateToken(username);
    }

    @GetMapping("/validate-token")
    public boolean validateToken(@RequestParam String token, @RequestParam String username) {
        return jwtService.validateToken(token, username);
    }
}
