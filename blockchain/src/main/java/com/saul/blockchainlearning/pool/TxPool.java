package com.saul.blockchainlearning.pool;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TxPool {
    public static int size = 0;
    public static List<TxwithPoolFormat> transactions = new ArrayList<>();

    public static void append(TxwithPoolFormat tx) {
        transactions.add(tx);
        size += 1;
    }
}
