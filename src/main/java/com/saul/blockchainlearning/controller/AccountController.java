package com.saul.blockchainlearning.controller;

import com.saul.blockchainlearning.dto.AccountDTO;
import com.saul.blockchainlearning.service.AccountService;
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

    @Autowired
    private AccountService accountService;

    @PostMapping("/account/get")
    public double getAccountMessage(@RequestParam("public_key") String key) {
        return transactionService.getUTXO(key);
    }

    @GetMapping("/account/create")
    public AccountDTO createAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountService.setKeyPair();
        accountDTO.setPrivate_key(accountService.getPrivate_key());
        accountDTO.setWif(accountService.getWIF());
        accountDTO.setPublic_key(accountService.getPublic_key());
        accountDTO.setAddress(accountService.getAddress());
        accountDTO.setUTXO(0.00d);
        return accountDTO;
    }
}
