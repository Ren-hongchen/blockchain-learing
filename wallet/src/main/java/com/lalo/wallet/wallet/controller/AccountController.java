package com.lalo.wallet.wallet.controller;

import com.lalo.wallet.wallet.dto.AccountDTO;
import com.lalo.wallet.wallet.dto.TransactionDTO;
import com.lalo.wallet.wallet.dto.UserTxDTO;
import com.lalo.wallet.wallet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account/get")
    public double getAccountMessage(@RequestParam("public_key") String key) {
        return 0.00;
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

    @PostMapping("/transaction/send")
    public void createTransaction(UserTxDTO userTxDTO) {

        //broadcast
    }
}
