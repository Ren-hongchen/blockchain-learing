package com.saul.blockchainlearning.service;


import com.saul.blockchainlearning.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;

    public List<> getUTXOList(String key) {

    }


    public String getLockScript(String key) {
        //transactionMapper.initLevelDB();
        return new String();
    }
}
