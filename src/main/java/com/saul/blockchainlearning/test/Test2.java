package com.saul.blockchainlearning.test;

import com.saul.blockchainlearning.pool.TxPool;
import org.springframework.beans.factory.annotation.Autowired;

public class Test2 {
    @Autowired
    private TxPool txPool;

    public void get() {
        System.out.println(txPool.getTransactions().toString());
    }
}
