package com.wrx.paymentsystem.controller;

import java.math.BigDecimal;  // Added import

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wrx.paymentsystem.dto.DepositRequest;
import com.wrx.paymentsystem.model.Wallet;
import com.wrx.paymentsystem.service.WalletService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public Wallet getWallet(@PathVariable Long id) {
        return walletService.getWallet(id);
    }

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('USER')")
    public Wallet depositMoney(@RequestBody DepositRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        return walletService.deposit(request);
    }
}