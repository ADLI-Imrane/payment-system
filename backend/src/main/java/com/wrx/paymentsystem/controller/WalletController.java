package com.wrx.paymentsystem.controller;

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
    public Wallet getWallet(@PathVariable Long id) {
        return walletService.getWallet(id);
    }

    @PostMapping("/deposit")
    public Wallet depositMoney(@RequestBody DepositRequest request) {
        return walletService.deposit(request);
    }
}
