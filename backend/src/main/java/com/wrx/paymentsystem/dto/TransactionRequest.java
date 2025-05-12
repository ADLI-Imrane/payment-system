package com.wrx.paymentsystem.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Handles all money movement operations including transfers
 */
@Data
public class TransactionRequest {
    @NotNull(message = "Sender wallet ID cannot be null")
    private Long senderWalletId;
    
    @NotNull(message = "Receiver wallet ID cannot be null")
    private Long receiverWalletId;
    
    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;
    
    @NotBlank(message = "Description cannot be empty")
    private String description;
}