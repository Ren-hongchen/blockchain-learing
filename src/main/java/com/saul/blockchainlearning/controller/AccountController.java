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

    @PostMapping("/get")
    public double getAccountMessage(@RequestParam("public_key") String key) {
        return transactionService.getUTXO(key);
    }

    @GetMapping("/create")
    public AccountDTO createAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        String private_key = accountService.getPrivateKey();
        String public_key = accountService.getPublicKey(private_key);
        accountDTO.setPrivate_key(private_key);
        accountDTO.setWif(accountService.getWIF(private_key));
        accountDTO.setPublic_key(public_key);
        accountDTO.setAddress(accountService.getAddress(public_key));
        accountDTO.setUTXO(0.00d);
        return accountDTO;
    }
}
