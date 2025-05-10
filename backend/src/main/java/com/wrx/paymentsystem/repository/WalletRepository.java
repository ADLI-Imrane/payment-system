package com.wrx.paymentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wrx.paymentsystem.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
