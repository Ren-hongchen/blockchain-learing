package com.saul.blockchainlearning.pool;

import com.saul.blockchainlearning.dto.TransactionDTO;
import lombok.Data;

@Data
public class TxwithPoolFormat {
    private double fee;
    private int age;
    private int bytes;
    private double priority;
    private TransactionDTO transactionDTO;
}
