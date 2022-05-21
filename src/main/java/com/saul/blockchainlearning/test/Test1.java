package com.saul.blockchainlearning.test;

import com.saul.blockchainlearning.pool.TxPool;
import com.saul.blockchainlearning.pool.TxwithPoolFormat;
import org.springframework.beans.factory.annotation.Autowired;

public class Test1 {
    @Autowired
    private TxPool txPool;

    public void test_pool() {
        TxwithPoolFormat tx = new TxwithPoolFormat();
        tx.setBytes(10);
        tx.setAge(1);
        tx.setTransactionDTO(null);
        tx.setFee(0.00);
        tx.setPriority(0.00);
        txPool.append(tx);
    }
}
