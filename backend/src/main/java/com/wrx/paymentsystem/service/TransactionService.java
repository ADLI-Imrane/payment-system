package com.wrx.paymentsystem.service;

import com.wrx.paymentsystem.dto.TransactionRequest;
import com.wrx.paymentsystem.exception.ResourceNotFoundException;
import com.wrx.paymentsystem.model.Transaction;
import com.wrx.paymentsystem.model.Wallet;
import com.wrx.paymentsystem.repository.TransactionRepository;
import com.wrx.paymentsystem.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public Transaction makeTransaction(TransactionRequest request) {
        Wallet senderWallet = walletRepository.findById(request.getSenderWalletId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository.findById(request.getReceiverWalletId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver wallet not found"));

        BigDecimal amount = request.getAmount();

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in sender's wallet");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction transaction = new Transaction();
        transaction.setSenderWallet(senderWallet);
        transaction.setReceiverWallet(receiverWallet);
        transaction.setAmount(amount);
        transaction.setDescription(request.getDescription());

        return transactionRepository.save(transaction);
    }
}
