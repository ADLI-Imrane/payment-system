package com.wrx.paymentsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wrx.paymentsystem.model.Transaction;
import com.wrx.paymentsystem.model.Wallet;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderWalletOrReceiverWallet(Wallet senderWallet, Wallet receiverWallet);
}
