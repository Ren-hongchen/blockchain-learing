package com.saul.blockchainlearning.pool;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class TxPool {
    private int size;
    private List<TxwithPoolFormat> transactions;

    public void append(TxwithPoolFormat tx) {
        transactions.add(tx);
        size += 1;
    }
}
