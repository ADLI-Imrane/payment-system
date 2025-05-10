package com.wrx.paymentsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wrx.paymentsystem.dto.DepositRequest;
import com.wrx.paymentsystem.exception.ResourceNotFoundException;
import com.wrx.paymentsystem.model.Wallet;
import com.wrx.paymentsystem.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet getWallet(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: " + id));
    }

    @Transactional
    public Wallet deposit(DepositRequest request) {
        Wallet wallet = getWallet(request.getWalletId());
        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        return walletRepository.save(wallet);
    }
}
