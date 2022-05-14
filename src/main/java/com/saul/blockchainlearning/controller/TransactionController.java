package com.saul.blockchainlearning.controller;

import com.saul.blockchainlearning.dto.TransactionDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @PostMapping("/transaction")
    public void SetTransaction(TransactionDTO transactionDTO) {

    }
}
