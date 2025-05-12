package com.wrx.paymentsystem.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wrx.paymentsystem.dto.TransactionRequest;
import com.wrx.paymentsystem.exception.InsufficientBalanceException;
import com.wrx.paymentsystem.exception.ResourceNotFoundException;
import com.wrx.paymentsystem.model.Transaction;
import com.wrx.paymentsystem.model.Wallet;
import com.wrx.paymentsystem.repository.TransactionRepository;
import com.wrx.paymentsystem.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public Transaction makeTransaction(TransactionRequest request) 
            throws ResourceNotFoundException, InsufficientBalanceException {
        
        Wallet senderWallet = walletRepository.findById(request.getSenderWalletId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender wallet not found"));
        
        Wallet receiverWallet = walletRepository.findById(request.getReceiverWalletId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver wallet not found"));

        BigDecimal amount = request.getAmount();
        
        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient funds in sender's wallet");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));
        
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction transaction = Transaction.builder()
                .senderWallet(senderWallet)
                .receiverWallet(receiverWallet)
                .amount(amount)
                .description(request.getDescription())
                .build();
        
        return transactionRepository.save(transaction);
    }
}