package com.wrx.paymentsystem.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferRequest {
    private Long senderWalletId;
    private Long receiverWalletId;
    private BigDecimal amount;
    private String description;
}
