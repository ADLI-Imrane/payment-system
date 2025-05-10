package com.wrx.paymentsystem.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DepositRequest {
    private Long walletId;
    private BigDecimal amount;
}
