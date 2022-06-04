package com.lalo.wallet.wallet.controller;

import com.google.protobuf.util.JsonFormat;
import com.lalo.wallet.wallet.dto.AccountDTO;
import com.lalo.wallet.wallet.dto.TransactionDTO;
import com.lalo.wallet.wallet.dto.UserTxDTO;
import com.lalo.wallet.wallet.globalvariable.GlobalVariable;
import com.lalo.wallet.wallet.network.rpc_UTXO;
import com.lalo.wallet.wallet.dto.UTXO;
import com.lalo.wallet.wallet.network.UTXO_RPC;
import com.lalo.wallet.wallet.network.utxo_list;
import com.lalo.wallet.wallet.serialization.Serializer;
import com.lalo.wallet.wallet.service.AccountService;
import com.lalo.wallet.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService txService;

    @PostMapping("/account/utxo/get")
    public List<UTXO> getAccountUTXO(@RequestParam("public_key") String key) throws Exception {
        return GlobalVariable.utxoList;
    }

    @GetMapping("/account/balance/get")
    public double getBalance() {
        double value = 0.00;
        for(UTXO utxo : GlobalVariable.utxoList){
            value += utxo.getAmount();
        }
        return value;
    }


    @GetMapping("/account/rescan")
    public void AccountRescan(@RequestParam("public_key") String key) throws Exception {
        //rescan utxo from a specific node
        GlobalVariable.utxoList = null;

        utxo_list result = UTXO_RPC.getUTXO(key);
        List<rpc_UTXO> utxoList = result.getUtxoList();

        for(rpc_UTXO rpc_utxo : utxoList) {
            if(rpc_utxo.getSpendable()) {
                String jsonObject = JsonFormat.printer().print(rpc_utxo);
                UTXO utxo = Serializer.deserialize(jsonObject.getBytes(), UTXO.class);
                GlobalVariable.utxoList.add(utxo);
            }
        }
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
    public void createTransaction(UserTxDTO userTxDTO) throws Exception {
        TransactionDTO transactionDTO = txService.getDTO(userTxDTO);
        //broadcast
    }
}
