package com.wrx.paymentsystem.service;

import com.wrx.paymentsystem.dto.DepositRequest;
import com.wrx.paymentsystem.exception.ResourceNotFoundException;
import com.wrx.paymentsystem.model.Wallet;
import com.wrx.paymentsystem.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet getWallet(Long id) throws ResourceNotFoundException {
        return walletRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: " + id));
    }

    @Transactional
    public Wallet deposit(DepositRequest request) throws ResourceNotFoundException {
        Wallet wallet = getWallet(request.getWalletId());
        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        return walletRepository.save(wallet);
    }

    public List<Wallet> getWalletsByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }

    public BigDecimal getTotalBalanceByUserId(Long userId) {
        return walletRepository.getTotalBalanceByUserId(userId)
                .orElse(BigDecimal.ZERO);
    }
}