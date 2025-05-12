package com.wrx.paymentsystem.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wrx.paymentsystem.dto.UserRegistrationRequest;
import com.wrx.paymentsystem.exception.DuplicateEntityException;
import com.wrx.paymentsystem.exception.ResourceNotFoundException;
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
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationRequest request) 
            throws DuplicateEntityException {
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEntityException("Email already exists");
        }
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateEntityException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        User savedUser = userRepository.save(user);
        
        // Create default wallet
        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        walletRepository.save(wallet);
        
        return savedUser;
    }

    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}