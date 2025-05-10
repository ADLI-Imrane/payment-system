package com.wrx.paymentsystem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wrx.paymentsystem.dto.UserRegistrationRequest;
import com.wrx.paymentsystem.dto.UserResponse;
import com.wrx.paymentsystem.model.User;
import com.wrx.paymentsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRegistrationRequest request) {
        User user = userService.registerUser(request);
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}
