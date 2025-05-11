package com.wrx.paymentsystem.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Encrypt password before saving
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        // Save the user
        User savedUser = userRepository.save(user);

        // Create and save a wallet for the user
        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        walletRepository.save(wallet);

        return savedUser;
    }
}
