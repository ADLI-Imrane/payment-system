package com.wrx.paymentsystem.service;

import org.springframework.stereotype.Service;

import com.wrx.paymentsystem.dto.UserRegistrationRequest;
import com.wrx.paymentsystem.model.User;
import com.wrx.paymentsystem.model.Wallet;
import com.wrx.paymentsystem.repository.UserRepository;
import com.wrx.paymentsystem.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public User registerUser(UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Encrypt password later for security
        User savedUser = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        walletRepository.save(wallet);

        return savedUser;
    }
}
