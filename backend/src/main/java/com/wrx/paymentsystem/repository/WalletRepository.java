package com.wrx.paymentsystem.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wrx.paymentsystem.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findByUserId(Long userId);
    
    @Query("SELECT SUM(w.balance) FROM Wallet w WHERE w.user.id = :userId")
    Optional<BigDecimal> getTotalBalanceByUserId(@Param("userId") Long userId);
}