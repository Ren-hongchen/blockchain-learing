package com.saul.blockchainlearning.controller;

import com.saul.blockchainlearning.dto.AccountDTO;
import com.saul.blockchainlearning.dto.KeyPairDTO;
import com.saul.blockchainlearning.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/get")
    public AccountDTO getAccountMessage(@RequestParam("public_key") String key) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setPublic_key(key);
        double UTXO = transactionService.getUTXO(key);
        accountDTO.setUTXO(UTXO);
        return accountDTO;
    }

    @GetMapping("/create")
    public KeyPairDTO createAccount() {
        KeyPairDTO keyPairDTO = new KeyPairDTO();
        return keyPairDTO;
    }
}
