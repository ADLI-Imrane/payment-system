package com.wrx.paymentsystem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wrx.paymentsystem.dto.TransactionRequest;
import com.wrx.paymentsystem.model.Transaction;
import com.wrx.paymentsystem.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public Transaction transferMoney(@RequestBody TransactionRequest request) {
        return transactionService.makeTransaction(request);
    }
}
