package com.wrx.paymentsystem.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wrx.paymentsystem.model.Transaction;
import com.wrx.paymentsystem.model.Transaction.TransactionStatus;
import com.wrx.paymentsystem.model.Wallet;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderWalletOrReceiverWallet(Wallet senderWallet, Wallet receiverWallet);

    List<Transaction> findByStatus(TransactionStatus status);

    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.senderWallet = :wallet OR t.receiverWallet = :wallet) " +
           "AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findWalletTransactionsBetweenDates(
        @Param("wallet") Wallet wallet,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE " +
           "t.senderWallet = :wallet AND t.status = 'COMPLETED'")
    BigDecimal getTotalSentAmount(@Param("wallet") Wallet wallet);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE " +
           "t.receiverWallet = :wallet AND t.status = 'COMPLETED'")
    BigDecimal getTotalReceivedAmount(@Param("wallet") Wallet wallet);
}